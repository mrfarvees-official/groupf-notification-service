package org.groupf.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void notification_shouldSetAndGetFieldsCorrectly() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 27, 10, 30);
        Notification notification = new Notification();

        notification.setId(1L);
        notification.setTitle("Order Created");
        notification.setMessage("Order order_0001 created successfully");
        notification.setType("ORDER_CREATED");
        notification.setCreatedAt(createdAt);

        assertEquals(1L, notification.getId());
        assertEquals("Order Created", notification.getTitle());
        assertEquals("Order order_0001 created successfully", notification.getMessage());
        assertEquals("ORDER_CREATED", notification.getType());
        assertEquals(createdAt, notification.getCreatedAt());
    }

    @Test
    void allArgsConstructor_shouldCreateNotificationCorrectly() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 27, 10, 30);

        Notification notification = new Notification(
                1L,
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED",
                createdAt
        );

        assertEquals(1L, notification.getId());
        assertEquals("Order Created", notification.getTitle());
        assertEquals("Order order_0001 created successfully", notification.getMessage());
        assertEquals("ORDER_CREATED", notification.getType());
        assertEquals(createdAt, notification.getCreatedAt());
    }

    @Test
    void prePersist_whenCreatedAtIsNull_shouldSetCreatedAt() {
        Notification notification = new Notification();

        notification.prePersist();

        assertNotNull(notification.getCreatedAt());
    }

    @Test
    void prePersist_whenCreatedAtAlreadyExists_shouldKeepExistingCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 27, 10, 30);
        Notification notification = new Notification();
        notification.setCreatedAt(createdAt);

        notification.prePersist();

        assertEquals(createdAt, notification.getCreatedAt());
    }
}
