package com.rabbit.demo.controller;

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
        Message message = new Message(content.toString().getBytes());
        rabbitTemplate.send(routingKeys.ROUTING_KEY_PUBLISHER, message);
        return ResponseEntity.ok(content);
    }

}
