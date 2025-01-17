package fs19.java.backend.application;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiver {

    @RabbitListener(queues = "generalQueue")
    public void receiveNotification(NotificationDTO notificationDTO) {
        System.out.println("Received notification: " + notificationDTO);
        // Handle the notification (e.g., sending an email)
    }
}
