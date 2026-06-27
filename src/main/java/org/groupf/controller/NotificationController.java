package org.groupf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.groupf.dto.NotificationRequest;
import org.groupf.dto.NotificationResponse;
import org.groupf.entity.Notification;
import org.groupf.repository.NotificationRepository;
import org.groupf.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping
    public Page<NotificationResponse> viewNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return notificationRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @PostMapping("/log")
    public NotificationResponse logNotification(@Valid @RequestBody NotificationRequest request) {
        Notification notification = notificationService.logNotification(
                request.title(),
                request.message(),
                request.type()
        );
        return toResponse(notification);
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getCreatedAt()
        );
    }
}
