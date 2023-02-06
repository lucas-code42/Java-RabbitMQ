package com.rabbit.demo.amqp;


import com.rabbit.demo.routingKeys.RoutingKeys;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Documentation https://docs.spring.io/spring-amqp/reference/html/#amqp-abstractions

@Configuration
public class AmqpConfiguration {

    @Autowired RoutingKeys routingKeys;

    @Bean
    public Queue createQueue() {
        return new Queue(routingKeys.ROUTING_KEY_PUBLISHER, false);
//      return QueueBuilder.nonDurable("test.publisher").build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin) {
        return  event -> rabbitAdmin.initialize();
    }
}
