package org.groupf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.groupf.entity.Notification;
import org.groupf.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification logNotification(String title, String message, String type) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);

        Notification savedNotification = notificationRepository.save(notification);

        log.info(
                "Notification logged: id={}, title={}, type={}, message={}",
                savedNotification.getId(),
                savedNotification.getTitle(),
                savedNotification.getType(),
                savedNotification.getMessage()
        );

        return savedNotification;
    }
}
