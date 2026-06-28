package org.groupf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.groupf.dto.NotificationRequest;
import org.groupf.entity.Notification;
import org.groupf.repository.NotificationRepository;
import org.groupf.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotificationRepository notificationRepository;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void viewNotifications_shouldReturnPagedNotifications() throws Exception {
        Notification notification = new Notification(
                1L,
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED",
                LocalDateTime.of(2026, 6, 27, 10, 30)
        );

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        when(notificationRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(List.of(notification), pageRequest, 1));

        mockMvc.perform(get("/api/notifications")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Order Created"))
                .andExpect(jsonPath("$.content[0].message").value("Order order_0001 created successfully"))
                .andExpect(jsonPath("$.content[0].type").value("ORDER_CREATED"));

        verify(notificationRepository, times(1))
                .findAll(pageRequest);
    }

    @Test
    void logNotification_shouldSaveAndReturnNotification() throws Exception {
        NotificationRequest request = new NotificationRequest(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        Notification savedNotification = new Notification(
                1L,
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED",
                LocalDateTime.of(2026, 6, 27, 10, 30)
        );

        when(notificationService.logNotification(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        )).thenReturn(savedNotification);

        mockMvc.perform(post("/api/notifications/log")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Order Created"))
                .andExpect(jsonPath("$.message").value("Order order_0001 created successfully"))
                .andExpect(jsonPath("$.type").value("ORDER_CREATED"));

        verify(notificationService, times(1)).logNotification(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );
    }

    @Test
    void logNotification_withInvalidRequest_shouldReturnBadRequest() throws Exception {
        NotificationRequest request = new NotificationRequest(
                "",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        mockMvc.perform(post("/api/notifications/log")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(notificationService, never())
                .logNotification(any(), any(), any());
    }
}
