package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.notifications.CreateNotificationDTO;
import booker.BookingApp.dto.notifications.NotificationDTO;
import booker.BookingApp.dto.users.UserDTO;
import booker.BookingApp.model.notifications.Notification;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.NotificationRepository;
import booker.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;
    @Override
    public ArrayList<NotificationDTO> getAll() {
        ArrayList<NotificationDTO> notifications = new ArrayList<>();
        NotificationDTO n1 = new NotificationDTO(1L, 3L, null, null, "Someone rated you.");
        NotificationDTO n2 = new NotificationDTO(2L, 2L, null,null, "Someone rated your accommodation.");
        NotificationDTO n3 = new NotificationDTO(3L, 1L, null,null, "Someone rated you.");
        notifications.add(n1);
        notifications.add(n2);
        notifications.add(n3);
        return notifications;
    }

    @Override
    public ArrayList<NotificationDTO> findAllForUser(Long userId) {
        ArrayList<NotificationDTO> notifications = getAll();
        ArrayList<NotificationDTO> personalNotifications = new ArrayList<>();
        // check userId for every notification
        for(NotificationDTO n : notifications){
            if(Objects.equals(n.getUserId(), userId)){
                personalNotifications.add(n);
            }
        }
        return personalNotifications;
    }

    @Override
    public NotificationDTO create(CreateNotificationDTO createNotificationDTO) {
        Notification notification = new Notification();
        User user = userService.findOne(createNotificationDTO.getUserId());
        notification.setUser(user);
        notification.setTime(new Date());
        notification.setTitle(createNotificationDTO.getTitle());
        notification.setContent(createNotificationDTO.getContent());

        notificationRepository.save(notification);
        NotificationDTO notificationDTO = NotificationDTO.makeFromNotification(notification);
        return notificationDTO;
    }
}
