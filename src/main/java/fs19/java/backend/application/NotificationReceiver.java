package fs19.java.backend.application;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationReceiver {

    @RabbitListener(queues = "generalQueue")
    public void receiveNotification(String message) {
        System.out.println("Received notification: " + message);
        // Handle the notification (e.g., sending an email)
    }
}
