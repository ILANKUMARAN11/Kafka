package com.ilan.producerAro;

import avro.schema.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Order(2)
@Component("AvroKafkaPublisher")
@Slf4j
public class KafkaPublisher {

    @Qualifier("AvroKafkaTemplate")
    @Autowired
    KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${kafka.topic-name}")
    String topicName;


    public void sendMessageWithCallback(String key, Person person) {
        ListenableFuture<SendResult<String, Person>> future =
                kafkaTemplate.send(topicName,key, person);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Person>>() {
            @Override
            public void onSuccess(SendResult<String, Person> result) {
                log.info("Message [{}] delivered to Topic [{}], Partition number [{}] sitting on offset [{}]",
                        person.toString(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}",
                        person.toString(),
                        ex.getMessage());
            }
        });
    }
}

