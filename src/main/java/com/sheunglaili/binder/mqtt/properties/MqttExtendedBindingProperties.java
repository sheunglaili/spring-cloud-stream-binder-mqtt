package com.sheunglaili.binder.mqtt.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.AbstractExtendedBindingProperties;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;

import java.util.Map;

@ConfigurationProperties("spring.cloud.stream.mqtt")
public class MqttExtendedBindingProperties extends
        AbstractExtendedBindingProperties<MqttSourceProperties, MqttSinkProperties, MqttBindingProperties> {

    private static final String DEFAULTS_PREFIX = "spring.cloud.stream.mqtt.default";

    @Override
    public String getDefaultsPrefix() {
        return DEFAULTS_PREFIX;
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return MqttBindingProperties.class;
    }

    @Override
    public Map<String, MqttBindingProperties> getBindings() {
        return this.doGetBindings();
    }
}
