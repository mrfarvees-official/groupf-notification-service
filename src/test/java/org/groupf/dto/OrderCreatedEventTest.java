package org.groupf.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderCreatedEventTest {

    @Test
    void orderCreatedEvent_shouldReturnRecordValues() {
        LocalDateTime orderDate = LocalDateTime.of(2026, 6, 27, 10, 30);

        OrderCreatedEvent event = new OrderCreatedEvent(
                "order_0001",
                "customer_0001",
                "product_0001",
                "Laptop",
                2,
                orderDate,
                "PENDING"
        );

        assertEquals("order_0001", event.orderId());
        assertEquals("customer_0001", event.customerId());
        assertEquals("product_0001", event.productId());
        assertEquals("Laptop", event.productName());
        assertEquals(2, event.quantity());
        assertEquals(orderDate, event.orderDate());
        assertEquals("PENDING", event.status());
    }
}
