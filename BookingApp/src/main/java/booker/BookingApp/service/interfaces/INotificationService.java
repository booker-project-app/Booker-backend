package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.notifications.NotificationDTO;
import booker.BookingApp.dto.users.UserDTO;
import booker.BookingApp.model.notifications.Notification;

import java.util.ArrayList;

public interface INotificationService {
    ArrayList<NotificationDTO> findAllForUser(Long userId);

    void save(Notification notification);
}
