package com.sheunglaili.binder.mqtt.config;


import com.sheunglaili.binder.mqtt.MqttBinder;
import com.sheunglaili.binder.mqtt.MqttProvisioningProvider;
import com.sheunglaili.binder.mqtt.properties.MqttBinderConfigurationProperties;
import com.sheunglaili.binder.mqtt.properties.MqttExtendedBindingProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.util.ObjectUtils;

/**
 * Mqtt binder configuration class
 * @author Alex , Li Sheung Lai
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
@EnableConfigurationProperties({
        MqttExtendedBindingProperties.class,
        MqttBinderConfigurationProperties.class
})
public class MqttBinderConfiguration {

    @Autowired
    private MqttExtendedBindingProperties mqttExtendedBindingProperties;

    @Bean
    public MqttProvisioningProvider provisioningProvider(){
        return new MqttProvisioningProvider();
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttBinderConfigurationProperties mqttProperties) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getUrl());
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setCleanSession(mqttProperties.isCleanSession());
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        if (ObjectUtils.nullSafeEquals(mqttProperties.getPersistence(), "file")) {
            factory.setPersistence(new MqttDefaultFilePersistence(mqttProperties.getPersistenceDirectory()));
        }
        else if (ObjectUtils.nullSafeEquals(mqttProperties.getPersistence(), "memory")) {
            factory.setPersistence(new MemoryPersistence());
        }
        return factory;
    }

    @Bean
    public MqttBinder mqttBinder(
            MqttPahoClientFactory mqttPahoClientFactory,
            MqttProvisioningProvider provisioningProvider){
        MqttBinder mqttBinder =  new MqttBinder(
                mqttPahoClientFactory,
                provisioningProvider
        );
        mqttBinder.setExtendedBindingProperties(this.mqttExtendedBindingProperties);
        return mqttBinder;
    }

}
