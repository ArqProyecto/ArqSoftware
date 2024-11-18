package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess;


import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Encuentra todas las notificaciones que deben enviarse
    @Query("SELECT n FROM Notification n WHERE n.date <= :currentDate AND n.status = 'PENDING'")
    List<Notification> findNotificationsToSend(LocalDateTime currentDate);

    // Encuentra todas las notificaciones de un usuario específico
    List<Notification> findByUserId(Long userId);
}
