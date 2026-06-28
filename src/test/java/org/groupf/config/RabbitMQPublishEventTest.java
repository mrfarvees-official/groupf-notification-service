package org.groupf.config;

import org.groupf.dto.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RabbitMQPublishEventTest {

    @Test
    void jsonMessageConverter_shouldConvertOrderCreatedEvent() {
        RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();
        Jackson2JsonMessageConverter converter = rabbitMQConfig.jsonMessageConverter();
        OrderCreatedEvent event = new OrderCreatedEvent(
                "order_0001",
                "customer_0001",
                "product_0001",
                "Laptop",
                2,
                LocalDateTime.of(2026, 6, 27, 10, 30),
                "PENDING"
        );

        Message message = converter.toMessage(event, new MessageProperties());

        assertEquals(OrderCreatedEvent.class.getName(), message.getMessageProperties().getHeader("__TypeId__"));
    }
}
