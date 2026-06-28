package org.groupf.service;

import org.groupf.entity.Notification;
import org.groupf.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void logNotification_shouldSaveNotificationAndReturnSavedNotification() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 27, 10, 30);
        Notification savedNotification = new Notification(
                1L,
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED",
                createdAt
        );

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(savedNotification);

        Notification result = notificationService.logNotification(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Order Created", result.getTitle());
        assertEquals("Order order_0001 created successfully", result.getMessage());
        assertEquals("ORDER_CREATED", result.getType());
        assertEquals(createdAt, result.getCreatedAt());

        verify(notificationRepository, times(1))
                .save(any(Notification.class));
    }

    @Test
    void logNotification_shouldMapInputValuesToEntityBeforeSaving() {
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        notificationService.logNotification(
                "Product Updated",
                "Product product_0001 updated successfully",
                "PRODUCT_UPDATED"
        );

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(1)).save(captor.capture());

        Notification notification = captor.getValue();
        assertNull(notification.getId());
        assertEquals("Product Updated", notification.getTitle());
        assertEquals("Product product_0001 updated successfully", notification.getMessage());
        assertEquals("PRODUCT_UPDATED", notification.getType());
    }
}
