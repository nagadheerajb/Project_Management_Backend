package fs19.java.backend.application;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "generalExchange";
    private static final String ROUTING_KEY = "generalRoutingKey";

    // Existing method to send a NotificationDTO
    public void sendNotification(NotificationDTO notificationDTO) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, notificationDTO);
        System.out.println("Sent notification DTO: " + notificationDTO);
    }

    // Overloaded method to send a string (backward compatibility)
    public void sendNotification(String messageContent) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setContent(messageContent);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, notificationDTO);
        System.out.println("Sent notification (converted from String): " + notificationDTO);
    }
}
