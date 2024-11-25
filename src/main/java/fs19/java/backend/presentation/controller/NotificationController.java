package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.application.service.NotificationService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Notifications", description = "Manage notifications")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private static final Logger logger = LogManager.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Create a notification", description = "Creates a new notification with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<NotificationDTO>> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        logger.info("Received create notification request: {}", notificationDTO);
        NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
        logger.info("Returning created notification with ID: {}", createdNotification.getId());
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdNotification), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a notification", description = "Updates the details of an existing notification.")
    @PutMapping("/{notificationId}")
    public ResponseEntity<GlobalResponse<NotificationDTO>> updateNotification(@PathVariable UUID notificationId, @Valid @RequestBody NotificationDTO notificationDTO) {
        logger.info("Received update notification request for ID: {} and DTO: {}", notificationId, notificationDTO);
        NotificationDTO updatedNotification = notificationService.updateNotification(notificationId, notificationDTO);
        logger.info("Returning updated notification with ID: {}", updatedNotification.getId());
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedNotification), HttpStatus.OK);
    }

    @Operation(summary = "Get a notification by ID", description = "Retrieves the details of a notification by its ID.")
    @GetMapping("/{notificationId}")
    public ResponseEntity<GlobalResponse<NotificationDTO>> getNotificationById(@PathVariable UUID notificationId) {
        logger.info("Received request to get notification with ID: {}", notificationId);
        NotificationDTO notification = notificationService.getNotificationById(notificationId);
        logger.info("Notification retrieved successfully: {}", notification);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), notification), HttpStatus.OK);
    }

    @Operation(summary = "Get all notifications", description = "Retrieves the details of all notifications.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<NotificationDTO>>> getAllNotifications() {
        logger.info("Received request to get all notifications");
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        logger.info("All notifications retrieved successfully");
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), notifications), HttpStatus.OK);
    }

    @Operation(summary = "Delete a notification", description = "Deletes a notification by its ID.")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<GlobalResponse<Void>> deleteNotification(@PathVariable UUID notificationId) {
        logger.info("Received request to delete notification with ID: {}", notificationId);
        notificationService.deleteNotification(notificationId);
        logger.info("Notification deleted successfully with ID: {}", notificationId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}