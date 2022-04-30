package com.ilan.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaProperties {

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

}
