package org.groupf.repository;

import org.groupf.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void saveNotification_shouldGenerateIdAndCreatedAt() {
        Notification notification = new Notification();
        notification.setTitle("Order Created");
        notification.setMessage("Order order_0001 created successfully");
        notification.setType("ORDER_CREATED");

        Notification savedNotification = notificationRepository.saveAndFlush(notification);

        assertNotNull(savedNotification.getId());
        assertNotNull(savedNotification.getCreatedAt());
        assertEquals("Order Created", savedNotification.getTitle());
        assertEquals("Order order_0001 created successfully", savedNotification.getMessage());
        assertEquals("ORDER_CREATED", savedNotification.getType());
    }

    @Test
    void findById_shouldReturnSavedNotification() {
        Notification notification = new Notification();
        notification.setTitle("Product Updated");
        notification.setMessage("Product product_0001 updated successfully");
        notification.setType("PRODUCT_UPDATED");

        Notification savedNotification = notificationRepository.saveAndFlush(notification);

        Notification foundNotification = notificationRepository
                .findById(savedNotification.getId())
                .orElseThrow();

        assertEquals(savedNotification.getId(), foundNotification.getId());
        assertEquals("Product Updated", foundNotification.getTitle());
        assertEquals("Product product_0001 updated successfully", foundNotification.getMessage());
        assertEquals("PRODUCT_UPDATED", foundNotification.getType());
    }

    @Test
    void findAll_withPageable_shouldReturnPagedNotifications() {
        Notification firstNotification = new Notification();
        firstNotification.setTitle("Order Created");
        firstNotification.setMessage("Order order_0001 created successfully");
        firstNotification.setType("ORDER_CREATED");

        Notification secondNotification = new Notification();
        secondNotification.setTitle("Product Updated");
        secondNotification.setMessage("Product product_0001 updated successfully");
        secondNotification.setType("PRODUCT_UPDATED");

        notificationRepository.save(firstNotification);
        notificationRepository.save(secondNotification);
        notificationRepository.flush();

        Page<Notification> notificationPage = notificationRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        assertEquals(2, notificationPage.getTotalElements());
        assertEquals(2, notificationPage.getContent().size());
    }

    @Test
    void deleteNotification_shouldRemoveNotification() {
        Notification notification = new Notification();
        notification.setTitle("Order Created");
        notification.setMessage("Order order_0001 created successfully");
        notification.setType("ORDER_CREATED");

        Notification savedNotification = notificationRepository.saveAndFlush(notification);

        notificationRepository.delete(savedNotification);
        notificationRepository.flush();

        assertFalse(notificationRepository.findById(savedNotification.getId()).isPresent());
    }
}
