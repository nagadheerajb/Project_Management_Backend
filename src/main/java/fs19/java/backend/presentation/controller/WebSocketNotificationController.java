package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketNotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(NotificationDTO notificationDTO) {
        messagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }
}
