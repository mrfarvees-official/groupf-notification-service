package org.groupf.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationResponseTest {

    @Test
    void notificationResponse_shouldReturnRecordValues() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 27, 10, 30);

        NotificationResponse response = new NotificationResponse(
                1L,
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED",
                createdAt
        );

        assertEquals(1L, response.id());
        assertEquals("Order Created", response.title());
        assertEquals("Order order_0001 created successfully", response.message());
        assertEquals("ORDER_CREATED", response.type());
        assertEquals(createdAt, response.createdAt());
    }
}
