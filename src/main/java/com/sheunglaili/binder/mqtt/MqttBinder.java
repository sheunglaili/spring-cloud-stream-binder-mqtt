package com.sheunglaili.binder.mqtt;


import com.sheunglaili.binder.mqtt.properties.MqttExtendedBindingProperties;
import com.sheunglaili.binder.mqtt.properties.MqttSinkProperties;
import com.sheunglaili.binder.mqtt.properties.MqttSourceProperties;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


public class MqttBinder
        extends AbstractMessageChannelBinder<ExtendedConsumerProperties<MqttSourceProperties>, ExtendedProducerProperties<MqttSinkProperties>, MqttProvisioningProvider>
        implements ExtendedPropertiesBinder<MessageChannel, MqttSourceProperties, MqttSinkProperties> {

    private MqttExtendedBindingProperties extendedBindingProperties = new MqttExtendedBindingProperties();

    private MqttPahoClientFactory mqttPahoClientFactory;

    public MqttBinder(
            MqttPahoClientFactory factory,
            MqttProvisioningProvider provisioningProvider) {
        super(BinderHeaders.STANDARD_HEADERS, provisioningProvider);
        this.mqttPahoClientFactory = factory;
    }

    @Override
    protected MessageHandler createProducerMessageHandler(
            ProducerDestination destination,
            ExtendedProducerProperties<MqttSinkProperties> producerProperties,
            MessageChannel errorChannel) throws Exception {

        MqttSinkProperties sinkProperties = producerProperties.getExtension();

        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter(
                sinkProperties.getQos(),
                sinkProperties.isRetained(),
                sinkProperties.getCharset()
        );

        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                sinkProperties.getClientId(),
                this.mqttPahoClientFactory
        );
        handler.setAsync(sinkProperties.isAsync());
        handler.setDefaultTopic(sinkProperties.getTopic());
        handler.setConverter(converter);
        return handler;
    }

    @Override
    protected MessageProducer createConsumerEndpoint(
            ConsumerDestination destination,
            String group,
            ExtendedConsumerProperties<MqttSourceProperties> properties) throws Exception {

        MqttSourceProperties sourceProperties = properties.getExtension();

        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter(
                sourceProperties.getCharset()
        );
        converter.setPayloadAsBytes(sourceProperties.isBinary());

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                sourceProperties.getClientId(),
                this.mqttPahoClientFactory,
                sourceProperties.getTopics()
        );

        adapter.setBeanFactory(this.getBeanFactory());
        adapter.setQos(sourceProperties.getQos());
        adapter.setConverter(converter);
        adapter.setOutputChannelName(destination.getName());
        return adapter;
    }

    public void setExtendedBindingProperties(MqttExtendedBindingProperties extendedBindingProperties) {
        this.extendedBindingProperties = extendedBindingProperties;
    }

    @Override
    public MqttSourceProperties getExtendedConsumerProperties(String channelName) {
        return this.extendedBindingProperties.getExtendedConsumerProperties(channelName);
    }

    @Override
    public MqttSinkProperties getExtendedProducerProperties(String channelName) {
        return this.extendedBindingProperties.getExtendedProducerProperties(channelName);
    }

    @Override
    public String getDefaultsPrefix() {
        return this.extendedBindingProperties.getDefaultsPrefix();
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return this.extendedBindingProperties.getExtendedPropertiesEntryClass();
    }

}
