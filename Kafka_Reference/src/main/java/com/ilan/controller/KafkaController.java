package com.ilan.controller;

import my.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/send")
public class KafkaController {

    @Qualifier("SimpleKafkaPublisher")
    @Autowired
    com.ilan.producerSimple.KafkaPublisher kafkaPublisher;

    @Qualifier("AvroKafkaPublisher")
    @Autowired
    com.ilan.producerAro.KafkaPublisher avroPublisher;

    @PostMapping(value = "/simple/string/{key}/{message}")
    public void sendSimpleMessage(@PathVariable Integer key, @PathVariable String message){
          kafkaPublisher.sendMessageWithCallback(key,message);
    }

    @PostMapping(value = "/avro/pojo/{key}")
    public void sendAvroMessage(@PathVariable String key, @RequestBody Person person){

        avro.schema.Address address =avro.schema.Address.newBuilder().
                setDoorNo("112/P6").
                setPinCode("606001").
                setStreet("Duragain Nagar").
                build();
        avro.schema.Person avroPerson = avro.schema.Person.newBuilder().setId(100l).
                setFullName("ILAN KUMARAN").
                setEmailId("ilankumaran.i@gmail.com").
                setPhoneNo("7539913146").
                setAddress(address).
                build();
        avroPublisher.sendMessageWithCallback(key, avroPerson);
    }
}
