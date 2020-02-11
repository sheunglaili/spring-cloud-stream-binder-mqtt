package com.sheunglaili.binder.mqtt.properties;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
//@ConfigurationProperties("mqtt.sink")
public class MqttSinkProperties {

    /**
     * identifies the client
     */
    @NotBlank
    @Size(min = 1 , max = 23)
    private String clientId = "stream.client.id";

    /**
     * the topic to which the sink will publish
     */
    @NotBlank
    private String topic = "stream.mqtt";

    /**
     *  the quality of service to use
     */
    @Range(min = 0 , max = 2)
    private int qos = 1;

    /**
     * whether to set the 'retained' flag
     */
    private boolean retained = false;

    /**
     * the charset used to convert a string payload
     */
    private String charset = "UTF-8";

    /**
     * whether or not to use async sends
     */
    private  boolean async = false;

}
