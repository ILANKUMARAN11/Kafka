package com.ilan.controller;

import com.ilan.config.KafkaProperties;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import my.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/send/avro")
public class AvroController {

    @Autowired
    KafkaProperties kafkaProperties;

    @Qualifier("AvroKafkaPublisher")
    @Autowired
    com.ilan.producerAro.KafkaPublisher avroPublisher;

    @ApiOperation(value = "API by RestTemplate", notes="fetch AccusedVo Info",nickname = "Avro Producer")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error !!!"),
            @ApiResponse(code = 404, message = "Service not found !!!"),
            @ApiResponse(code = 401, message = "Unauthorized access !!!"),
            @ApiResponse(code = 403, message = "Forbidden access !!!"),
            @ApiResponse(code = 200, message = "Successful retrieval !!!", responseContainer = "List") })
    @PostMapping(value = "/pojo/{key}")
    public void sendAvroMessage(@ApiParam(value = "Message Key", required = true, defaultValue = "ILAN KUMARAN")@PathVariable String key, @RequestBody Employee employee){

        avro.schema.Address address =avro.schema.Address.newBuilder().
                setDoorNo(employee.getAddress().getDoorNo()).
                setPinCode(employee.getAddress().getPinCode()).
                setStreet(employee.getAddress().getStreet()).
                build();
        avro.schema.Employee avroPerson = avro.schema.Employee.newBuilder().setId(employee.getId()).
                setFullName(employee.getFullName()).
                setEmailId(employee.getEmailId()).
                setPhoneNo(employee.getPhoneNo()).
                setAddress(address).
                build();
        avroPublisher.sendMessageWithCallback(kafkaProperties.getEmployeeTopicName(), key, avroPerson);
    }
}
