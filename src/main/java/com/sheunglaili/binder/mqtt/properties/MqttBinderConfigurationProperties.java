package com.sheunglaili.binder.mqtt.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

/**
 * Configuration properties for the Mqtt binder . The properties in the class
 * are prefixed with <b>spring.cloud.stream.mqtt.binder</b>
 * @author Alex , Li Sheung Lai
 */
@Slf4j
@Data
@Validated
@ConfigurationProperties(prefix = "spring.cloud.stream.mqtt.binder")
public class MqttBinderConfigurationProperties {

    /**
     * location of the mqtt broker(s) (comma-delimited list)
     */
    @Size(min = 1)
    private String[] url = new String[] { "tcp://localhost:1883" };

    /**
     * the username to use when connecting to the broker
     */
    private String username = "guest";

    /**
     * the password to use when connecting to the broker
     */
    private String password = "guest";

    /**
     * whether the client and server should remember state across restarts and reconnects
     */
    private boolean cleanSession = true;

    /**
     * the connection timeout in seconds
     */
    private int connectionTimeout = 30;

    /**
     * the ping interval in seconds
     */
    private int keepAliveInterval = 60;

    /**
     * 'memory' or 'file'
     */
    private String persistence = "memory";

    /**
     * Persistence directory
     */
    private String persistenceDirectory = "/tmp/paho";


}
