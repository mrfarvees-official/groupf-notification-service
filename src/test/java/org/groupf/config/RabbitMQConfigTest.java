package org.groupf.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    @Test
    void orderExchange_shouldReturnTopicExchange() {
        RabbitMQConfig rabbitMQConfig = createRabbitMQConfig();

        TopicExchange exchange = rabbitMQConfig.orderExchange();

        assertEquals("order.exchange", exchange.getName());
        assertTrue(exchange.isDurable());
    }

    @Test
    void notificationOrderQueue_shouldReturnDurableQueue() {
        RabbitMQConfig rabbitMQConfig = createRabbitMQConfig();

        Queue queue = rabbitMQConfig.notificationOrderQueue();

        assertEquals("notification.order.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void notificationOrderBinding_shouldBindQueueToExchangeWithRoutingKey() {
        RabbitMQConfig rabbitMQConfig = createRabbitMQConfig();

        Binding binding = rabbitMQConfig.notificationOrderBinding();

        assertEquals("notification.order.queue", binding.getDestination());
        assertEquals("order.exchange", binding.getExchange());
        assertEquals("order.created", binding.getRoutingKey());
    }

    @Test
    void jsonMessageConverter_shouldReturnJacksonConverter() {
        RabbitMQConfig rabbitMQConfig = createRabbitMQConfig();

        Jackson2JsonMessageConverter converter = rabbitMQConfig.jsonMessageConverter();

        assertNotNull(converter);
    }

    private RabbitMQConfig createRabbitMQConfig() {
        RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();
        ReflectionTestUtils.setField(rabbitMQConfig, "orderExchange", "order.exchange");
        ReflectionTestUtils.setField(rabbitMQConfig, "notificationOrderQueue", "notification.order.queue");
        ReflectionTestUtils.setField(rabbitMQConfig, "orderCreatedRoutingKey", "order.created");
        return rabbitMQConfig;
    }
}
