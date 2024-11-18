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
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private User user;
    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    public void setUp() {
        // Crear un usuario de prueba
        user = new User();
        user.setId(1L);
        
        // Crear notificaciones de prueba
        notification1 = new Notification();
        notification1.setId(1L);
        notification1.setMessage("Notificación 1");
        notification1.setType("TYPE1");
        notification1.setStatus("PENDING");
        notification1.setDate(LocalDateTime.now().minusHours(1));
        notification1.setUser(user);
        
        notification2 = new Notification();
        notification2.setId(2L);
        notification2.setMessage("Notificación 2");
        notification2.setType("TYPE2");
        notification2.setStatus("PENDING");
        notification2.setDate(LocalDateTime.now().minusHours(2));
        notification2.setUser(user);
    }

    @Test
    @Description("Test para obtener todas las notificaciones de un usuario")
    @Step("Obtener notificaciones para el usuario con ID {0}")
    public void testGetNotificationsForUser() {
        // Definir el comportamiento del mock para el repositorio
        when(notificationRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(notification1, notification2));

        // Llamar al método
        List<Notification> notifications = notificationService.getNotificationsForUser(user.getId());

        // Verificar los resultados
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(notification1));
        assertTrue(notifications.contains(notification2));

        // Verificar que el repositorio fue llamado
        verify(notificationRepository, times(1)).findByUserId(user.getId());
        
        // Registrar paso en Allure
        Allure.step("Notificaciones obtenidas correctamente para el usuario.");
    }

    @Test
    @Description("Test para enviar notificaciones pendientes")
    @Step("Enviar notificaciones pendientes")
    public void testSendPendingNotifications() {
        // Definir el comportamiento del mock para el repositorio
        when(notificationRepository.findNotificationsToSend(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(notification1, notification2));

        // Llamar al método
        notificationService.sendPendingNotifications();

        // Verificar que el estado de las notificaciones fue actualizado
        assertEquals("SENT", notification1.getStatus());
        assertEquals("SENT", notification2.getStatus());

        // Verificar que el repositorio fue llamado para guardar las notificaciones
        verify(notificationRepository, times(2)).save(any(Notification.class));
        
        // Registrar paso en Allure
        Allure.step("Notificaciones pendientes enviadas correctamente y su estado actualizado.");
    }
}
