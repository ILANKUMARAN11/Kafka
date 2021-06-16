package com.ilan.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(-1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaProperties {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String consumerBootstrapServers;

    @Value("${kafka.producer.bootstrap-servers}")
    private String producerBootstrapServers;

    @Value("${kafka.simple.topic-name}")
    private String simpleTopicName;

    @Value("${kafka.simple.group-id}")
    private String simpleGroupId;

    @Value("${kafka.employee.topic-name}")
    private String employeeTopicName;

    @Value("${kafka.employee.group-id}")
    private String employeeGroupId;



//    simple:
//    topic-name: JustSimple
//    group-id: JustSimple_group_id
//    employee:
//    topic-name: Employee
//    group-id: Employee_group_id
//    consumer:
//    bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
//    producer:
//    bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
}
