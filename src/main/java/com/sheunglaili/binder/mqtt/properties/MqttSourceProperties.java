package com.sheunglaili.binder.mqtt.properties;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
//@ConfigurationProperties("mqtt.source")
public class MqttSourceProperties {

    /**
     * identifies the client
     */
    @NotBlank
    @Size(min = 1,max = 23)
    private String clientId = "stream.client.id.source";

    /**
     * the topic(s) (comma-delimited) to which the source will subscribe
     */
    private String[] topics = new String[]{"stream.mqtt"};

    /**
     * the qos; a single value for all topics or a comma-delimited list to match the topics
     */
    private int[] qos = new int[]{0};

    /**
     * true to leave the payload as bytes
     */
    private boolean binary = false;

    /**
     * the charset used to convert bytes to String (when binary is false)
     */
    private String charset = "UTF-8";


}
