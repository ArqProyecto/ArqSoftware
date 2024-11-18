package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.NotificationRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Notification;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void sendPendingNotifications() {
        List<Notification> notifications = notificationRepository.findNotificationsToSend(LocalDateTime.now());
        for (Notification notification : notifications) {
            // Lógica para enviar notificación
            notification.setStatus("SENT");
            notificationRepository.save(notification);
        }
    }
}
