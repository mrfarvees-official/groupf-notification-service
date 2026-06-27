package org.groupf.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.groupf.dto.OrderCreatedEvent;
import org.groupf.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderNotificationListener {

    private final NotificationService notificationService;

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

        notificationService.logNotification(
                "Order Created",
                "Order " + event.orderId() + " created for customer " + event.customerId()
                        + " with product " + event.productName() + " and quantity " + event.quantity(),
                "ORDER_CREATED"
        );
    }
}
