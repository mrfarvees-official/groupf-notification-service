package org.groupf.listener;

import lombok.extern.slf4j.Slf4j;
import org.groupf.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(OrderNotificationListener.class);

    @RabbitListener(queues = "${rabbitmq.queue.notification-order}")
    public void receiveOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Received order created event: {}", event);

        log.info(
                "Sending notification for orderId={}, customerId={}, product={}, quanity={}, status={}",
                event.orderId(),
                event.customerId(),
                event.productName(),
                event.quantity(),
                event.status()
        );


    }
}
