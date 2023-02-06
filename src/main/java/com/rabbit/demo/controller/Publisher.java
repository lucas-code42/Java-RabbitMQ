package com.rabbit.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.demo.dto.PublisherDto;
import com.rabbit.demo.routingKeys.RoutingKeys;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/publisher")
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RoutingKeys routingKeys;

    @PostMapping
    public ResponseEntity<?> publisher(@RequestBody PublisherDto content) {
        String message = "null";

        try {
            ObjectMapper json = new ObjectMapper();
            message = json.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        Message queueMsg = new Message(message.getBytes());
        rabbitTemplate.send(routingKeys.ROUTING_KEY_PUBLISHER, queueMsg);
        return ResponseEntity.ok("msg published -> " + message);
    }

}
