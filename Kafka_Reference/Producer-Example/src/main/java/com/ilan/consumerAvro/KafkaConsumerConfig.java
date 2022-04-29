package com.ilan.consumerAvro;

import avro.schema.Employee;
import com.ilan.config.KafkaProperties;
import com.ilan.serializer.AvroDeserializer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Order(1)
@Slf4j
@Configuration("KafkaAvroConsumerConfig")
public class KafkaConsumerConfig {

    @Autowired
    KafkaProperties kafkaProperties;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getConsumerBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getEmployeeGroupId());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "not-used");
        return props;
    }

    @Bean("AvroConsumerFactory")
    public ConsumerFactory<String, Employee> consumerFactory() {
        //return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new AvroDeserializer<>(Employee.class));

    }

    @Bean("kafkaAvroListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Employee>> kafkaListenerContainerFactory(@Qualifier("AvroConsumerFactory") ConsumerFactory<String, Employee> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Employee> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordFilterStrategy(record -> {
            log.info("Filtering the message >>"+record.value());
            return record.value().getFullName().toString().equalsIgnoreCase("ILAN");
        });
//        factory.setRecordFilterStrategy(new RecordFilterStrategy<Integer, String>() {
//            @Override
//            public boolean filter(ConsumerRecord<Integer, String> consumerRecord) {
//                int data = Integer.parseInt((String) consumerRecord.value());
//                log.info("filterContainerFactory filter : "+data);
//                if (data % 2 == 0) {
//                    return false;
//                }else {
//                //Return true will be discarded
//                return true;
//                }
//            }
//        });
        return factory;
    }

}

