package com.ilan.controller;

import com.ilan.config.KafkaProperties;
import com.ilan.producerSimple.SimplePublisher;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/send/simple")
public class SimpleController {

    @Autowired
    KafkaProperties kafkaProperties;

    @Autowired
    SimplePublisher simplePublisher;


    @ApiOperation(value = "API by RestTemplate", notes="Key as Integer and Value as message",nickname = "Simple Producer")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error !!!"),
            @ApiResponse(code = 404, message = "Service not found !!!"),
            @ApiResponse(code = 401, message = "Unauthorized access !!!"),
            @ApiResponse(code = 403, message = "Forbidden access !!!"),
            @ApiResponse(code = 200, message = "Successful retrieval !!!", responseContainer = "List") })
    @PostMapping(value = "/string/{key}/{message}")
    public void sendSimpleMessage(@ApiParam(value = "Message Key", required = true, example = "12")@PathVariable Integer key,
                                  @ApiParam(value = "Message Data", required = true, defaultValue = "ILAN KUMARAN")@PathVariable String message){
          simplePublisher.sendMessageWithCallback(kafkaProperties.getSimpleTopicName(), key, message);
    }


}
