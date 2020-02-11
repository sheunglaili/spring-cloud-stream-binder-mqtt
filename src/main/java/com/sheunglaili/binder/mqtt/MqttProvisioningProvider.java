package com.sheunglaili.binder.mqtt;



import com.sheunglaili.binder.mqtt.properties.MqttSinkProperties;
import com.sheunglaili.binder.mqtt.properties.MqttSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class MqttProvisioningProvider implements
        ProvisioningProvider<ExtendedConsumerProperties<MqttSourceProperties>, ExtendedProducerProperties<MqttSinkProperties>> {

    @Override
    public ProducerDestination provisionProducerDestination(
            String name,
            ExtendedProducerProperties<MqttSinkProperties> properties) throws ProvisioningException {
        return new MqttTopicDestination(name);
    }

    @Override
    public ConsumerDestination provisionConsumerDestination(String name, String group, ExtendedConsumerProperties<MqttSourceProperties> properties) throws ProvisioningException {
        return new MqttTopicDestination(name);
    }

    @RequiredArgsConstructor
    private class MqttTopicDestination implements ProducerDestination , ConsumerDestination{

        private final String destination;

        @Override
        public String getName() {
            return this.destination.trim();
        }

        @Override
        public String getNameForPartition(int partition) {
            throw  new UnsupportedOperationException("Partitioning is not implemented for mqtt");
        }
    }
}
