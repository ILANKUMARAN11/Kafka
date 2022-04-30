package com.ilan.consumerAvro;

import avro.schema.Employee;
import com.ilan.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ConsumerAvroListener {

    @Autowired
    KafkaProperties kafkaProperties;

//    @KafkaListener(topics = "Employee")
//    void listener(Employee employee) {
//        log.info("Listener [{}]", employee.toString());
//    }

    @KafkaListener(topics = "Employee", groupId = "Employee_group_id", containerFactory = "kafkaAvroListenerContainerFactory")
    void listenerWithMessageConverter(Employee employee) {
        log.info("Message Received Employee Listener ::::::: [{}]", employee);
    }

//    @KafkaListener(topics = { "reflectoring-1", "reflectoring-2" }, groupId = "reflectoring-group-2")
//    void commonListenerForMultipleTopics(String message) {
//        log.info("MultipleTopicListener - [{}]", message);
//    }
//
//    @KafkaListener(topicPartitions = @TopicPartition(topic = "reflectoring-3", partitionOffsets = {
//            @PartitionOffset(partition = "0", initialOffset = "0") }), groupId = "reflectoring-group-3")
//    void listenToParitionWithOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
//                                    @Header(KafkaHeaders.OFFSET) int offset) {
//        log.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
//    }
//
//    @KafkaListener(topics = "reflectoring-bytes")
//    void listenerForRoutingTemplate(String message) {
//        log.info("RoutingTemplate BytesListener [{}]", message);
//    }
//
//    @KafkaListener(topics = "reflectoring-others")
//    @SendTo("reflectoring-2")
//    String listenAndReply(String message) {
//        log.info("ListenAndReply [{}]", message);
//        return "This is a reply sent to 'reflectoring-2' topic after receiving message at 'reflectoring-others' topic";
//    }
//

}
