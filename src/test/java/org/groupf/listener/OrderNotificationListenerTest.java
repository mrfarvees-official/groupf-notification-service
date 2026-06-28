package org.groupf.listener;

import org.groupf.dto.OrderCreatedEvent;
import org.groupf.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderNotificationListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderNotificationListener orderNotificationListener;

    @Test
    void receiveOrderCreatedEvent_shouldLogNotification() {
        OrderCreatedEvent event = new OrderCreatedEvent(
                "order_0001",
                "customer_0001",
                "product_0001",
                "Laptop",
                2,
                LocalDateTime.of(2026, 6, 27, 10, 30),
                "PENDING"
        );

        orderNotificationListener.receiveOrderCreatedEvent(event);

        verify(notificationService, times(1)).logNotification(
                "Order Created",
                "Order order_0001 created for customer customer_0001 with product Laptop and quantity 2",
                "ORDER_CREATED"
        );
    }
}
