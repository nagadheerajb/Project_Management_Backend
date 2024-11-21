package fs19.java.backend.application;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.application.mapper.NotificationMapper;
import fs19.java.backend.application.service.NotificationService;
import fs19.java.backend.domain.abstraction.NotificationRepository;
import fs19.java.backend.domain.abstraction.UserRepository;
import fs19.java.backend.domain.entity.Notification;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.exception.NotificationNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String NOTIFICATION_NOT_FOUND_MESSAGE = "Notification with ID %s not found";

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        User mentionedBy = userRepository.findById(notificationDTO.getMentionedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", notificationDTO.getMentionedBy())));
        User mentionedTo = userRepository.findById(notificationDTO.getMentionedTo())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", notificationDTO.getMentionedTo())));
        Notification notification = NotificationMapper.toEntity(notificationDTO);
        notification.setId(UUID.randomUUID());
        notification.setCreatedDate(ZonedDateTime.now());
        notification.setMentionedBy(mentionedBy.getId());
        notification.setMentionedTo(mentionedTo.getId());
        notificationRepository.save(notification);
        return NotificationMapper.toDTO(notification);
    }

    @Override
    public NotificationDTO updateNotification(UUID id, NotificationDTO notificationDTO) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id)));
        existingNotification.setContent(notificationDTO.getContent());
        existingNotification.setNotifyType(notificationDTO.getNotifyType());
        existingNotification.setRead(notificationDTO.isRead());
        existingNotification.setProjectId(notificationDTO.getProjectId());
        existingNotification.setMentionedBy(notificationDTO.getMentionedBy());
        existingNotification.setMentionedTo(notificationDTO.getMentionedTo());
        notificationRepository.save(existingNotification);
        return NotificationMapper.toDTO(existingNotification);
    }

    @Override
    public NotificationDTO getNotificationById(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id)));
        return NotificationMapper.toDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id));
        }
        notificationRepository.deleteById(id);
    }
}