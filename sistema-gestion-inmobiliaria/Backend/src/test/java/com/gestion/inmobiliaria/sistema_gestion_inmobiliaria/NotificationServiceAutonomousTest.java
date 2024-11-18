package com.gestion.inmobiliaria.sistema_gestion_inmobiliaria;

import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.business.NotificationService;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.dataaccess.NotificationRepository;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.Notification;
import com.gestion.inmobiliaria.sistema_gestion_inmobiliaria.persistance.User;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceAutonomousTest {

    @Mock
    private NotificationRepository notificationRepository;  // Simulamos el repository

    @InjectMocks
    private NotificationService notificationService;  // Servicio bajo prueba

    private User user;
    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    void setUp() {
        // Configuramos un usuario para la prueba
        user = new User();
        user.setId(1L);

        // Configuramos las notificaciones para el usuario
        notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUser(user);
        notification1.setMessage("Mensaje 1");
        notification1.setDate(LocalDateTime.now());
        notification1.setStatus("PENDING");
        notification1.setType("ALERT");

        notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUser(user);
        notification2.setMessage("Mensaje 2");
        notification2.setDate(LocalDateTime.now());
        notification2.setStatus("PENDING");
        notification2.setType("REMINDER");
    }

    @Test
    @Description("Prueba para obtener las notificaciones de un usuario")
    @Step("Obtenemos las notificaciones pendientes para el usuario")
    void testGetNotificationsForUser() {
        // Simulamos que el repository devuelve las notificaciones del usuario
        when(notificationRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(notification1, notification2));

        // Ejecutamos el método que estamos probando
        List<Notification> notifications = notificationService.getNotificationsForUser(user.getId());

        // Verificamos los resultados
        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        assertEquals("Mensaje 1", notifications.get(0).getMessage());
        assertEquals("Mensaje 2", notifications.get(1).getMessage());

        // Verificamos que el método repository fue llamado una vez
        verify(notificationRepository, times(1)).findByUserId(user.getId());

        Allure.step("Notificaciones obtenidas correctamente para el usuario.");
    }

    @Test
    @Description("Prueba para enviar notificaciones pendientes")
    @Step("Enviamos notificaciones pendientes")
    void testSendPendingNotifications() {
        // Simulamos que existen notificaciones pendientes
        when(notificationRepository.findNotificationsToSend(any())).thenReturn(Arrays.asList(notification1, notification2));

        // Ejecutamos el método que estamos probando
        notificationService.sendPendingNotifications();

        // Verificamos que se actualizó el estado de las notificaciones
        assertEquals("SENT", notification1.getStatus());
        assertEquals("SENT", notification2.getStatus());

        // Verificamos que el método save fue llamado correctamente
        verify(notificationRepository, times(2)).save(any(Notification.class));

        Allure.step("Estado de las notificaciones actualizado a 'SENT'.");
    }
}