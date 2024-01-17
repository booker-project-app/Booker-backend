package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.notifications.CreateNotificationDTO;
import booker.BookingApp.dto.notifications.NotificationDTO;
import booker.BookingApp.dto.users.UserDTO;

import java.util.ArrayList;

public interface INotificationService {
    ArrayList<NotificationDTO> getAll();
    ArrayList<NotificationDTO> findAllForUser(Long userId);
    NotificationDTO create(CreateNotificationDTO createNotificationDTO);
}
