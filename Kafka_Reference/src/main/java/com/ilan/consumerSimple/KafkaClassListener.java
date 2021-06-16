package com.ilan.consumerSimple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Order(1)
@Component
@KafkaListener(id = "Employee_group_id", topics = "JustSimple")
@Slf4j
class KafkaClassListener {

    @KafkaHandler
    void listen(String message) {
        log.info("ClassLevel Message Listener KafkaHandler[String] {}", message);
    }

    @KafkaHandler(isDefault = true)
    void listenDefault(Object object) {
        log.info("ClassLevel Message Listener KafkaHandler[Default] {}", object);
    }
}
