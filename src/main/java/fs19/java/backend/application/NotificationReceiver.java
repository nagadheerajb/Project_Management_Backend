package fs19.java.backend.application;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.presentation.controller.WebSocketNotificationController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiver {

    private final WebSocketNotificationController webSocketController;

    public NotificationReceiver(WebSocketNotificationController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @RabbitListener(queues = "generalQueue")
    public void receiveNotification(NotificationDTO notificationDTO) {
        System.out.println("Received notification from RabbitMQ: " + notificationDTO);

        // Forward to WebSocket clients
        webSocketController.sendNotification(notificationDTO);
    }
}
