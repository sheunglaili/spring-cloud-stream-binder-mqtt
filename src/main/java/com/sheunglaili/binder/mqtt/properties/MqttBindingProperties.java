package com.sheunglaili.binder.mqtt.properties;

import com.above.mqttkafka.config.properties.MqttSinkProperties;
import com.above.mqttkafka.config.properties.MqttSourceProperties;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;

/**
 * Container object for mqtt specific extend producer and consumer properties.
 * @author Alex , Li Sheung Lai
 */
public class MqttBindingProperties implements BinderSpecificPropertiesProvider {

   private MqttSourceProperties consumer = new MqttSourceProperties();

   private MqttSinkProperties producer = new MqttSinkProperties();

    @Override
    public MqttSourceProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(MqttSourceProperties consumer) {
        this.consumer = consumer;
    }

    @Override
    public MqttSinkProperties getProducer() {
        return producer;
    }

    public void setProducer(MqttSinkProperties producer) {
        this.producer = producer;
    }
}
