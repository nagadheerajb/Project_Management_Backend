package fs19.java.backend.application;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "generalExchange";
    private static final String ROUTING_KEY = "generalRoutingKey";

    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Sent notification: " + message);
    }
}
