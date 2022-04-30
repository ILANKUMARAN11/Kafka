package com.ilan.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSimpleConfig {

    @Autowired
    KafkaProperties kafkaProperties;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        //Only one in-flight messages per Kafka broker connection
        // - max.in.flight.requests.per.connection (default 5)
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        //Set the number of retries - retries
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        //Request timeout - request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

        //Only retry after one second.
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);

        props.put(ProducerConfig.ACKS_CONFIG,"-1");

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProducerBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    @Bean("SimpleProducerFactory")
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean("SimpleKafkaTemplate")
    public KafkaTemplate<Integer, String> kafkaTemplate(@Qualifier("SimpleProducerFactory") ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
