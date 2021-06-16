package com.ilan.producerSimple;

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
@Component("SimpleKafkaPublisher")
@Slf4j
public class KafkaPublisher {

    @Qualifier("SimpleKafkaTemplate")
    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessageWithCallback(String topicName, Integer key,String message) {
        ListenableFuture<SendResult<Integer, String>> future =
                kafkaTemplate.send(topicName,key,message);

        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("Message [{}] delivered to Topic [{}], Partition number [{}] sitting on offset [{}]",
                        message,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}",
                        message,
                        ex.getMessage());
            }
        });

    }
}

