package org.groupf.service;

import org.groupf.entity.Notification;
import org.groupf.repository.NotificationRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Test
    void logNotification_shouldUseRepositoryImplementationDependency() {
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        NotificationService notificationService = new NotificationService(notificationRepository);

        Notification savedNotification = new Notification();
        savedNotification.setId(1L);
        savedNotification.setTitle("Order Created");
        savedNotification.setMessage("Order order_0001 created successfully");
        savedNotification.setType("ORDER_CREATED");

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(savedNotification);

        Notification result = notificationService.logNotification(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        assertEquals(1L, result.getId());
        assertEquals("ORDER_CREATED", result.getType());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}
