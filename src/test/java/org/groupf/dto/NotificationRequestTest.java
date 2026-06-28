package org.groupf.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_shouldHaveNoValidationErrors() {
        NotificationRequest request = new NotificationRequest(
                "Order Created",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        Set<ConstraintViolation<NotificationRequest>> violations =
                validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void blankTitle_shouldFailValidation() {
        NotificationRequest request = new NotificationRequest(
                "",
                "Order order_0001 created successfully",
                "ORDER_CREATED"
        );

        Set<ConstraintViolation<NotificationRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Notification title is required"))
        );
    }

    @Test
    void blankMessage_shouldFailValidation() {
        NotificationRequest request = new NotificationRequest(
                "Order Created",
                "",
                "ORDER_CREATED"
        );

        Set<ConstraintViolation<NotificationRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Notification message is required"))
        );
    }

    @Test
    void blankType_shouldFailValidation() {
        NotificationRequest request = new NotificationRequest(
                "Order Created",
                "Order order_0001 created successfully",
                ""
        );

        Set<ConstraintViolation<NotificationRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Notification type is required"))
        );
    }
}
