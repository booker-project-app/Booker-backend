package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.notifications.NotificationDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.dto.users.OwnerDTO;
import booker.BookingApp.enums.NotificationType;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.notifications.Notification;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.NotificationRepository;
import booker.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    NotificationRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    GuestService guestService;

    @Autowired
    OwnerService ownerService;

    @Override
    public ArrayList<NotificationDTO> findAllForUser(Long userId) {
        List<Notification> notifications = repository.findAllPersonal(userId);
        ArrayList<NotificationDTO> personalNotifications = new ArrayList<>();
        // check userId for every notification
        for(Notification n : notifications){
            personalNotifications.add(NotificationDTO.makeFromNotification(n));
        }
        return personalNotifications;
    }

    @Override
    public void save(Notification notification) {
        repository.save(notification);
    }

    public Date secondsCalculator(Date date, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    @Override
    public NotificationDTO findNewForUser(Long userId) throws ParseException {
        if (userId == 0){
            return null;
        }
        List<Notification> notifications = repository.findAllPersonal(userId);
        if (notifications.isEmpty()){
            return null;
        }
        Notification notification = notifications.get(notifications.size()-1);
        String notifTime = notification.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean enabled = false;
        if (sdf.parse(notifTime).after(secondsCalculator(new Date(), -5))) {
            User user = this.userService.findOne(userId);
            if (user.getRole() == Role.GUEST) {
                GuestDTO guest = guestService.getGuestById(user.getId());
                enabled = guest.isNotificationEnabled();
            } else if (user.getRole() == Role.OWNER) {
                OwnerDTO owner = ownerService.getOwnerById(user.getId());
                if (notification.getType() == NotificationType.RESERVATION_REQUEST){
                    enabled = owner.isRequestNotificationEnabled();
                } else if (notification.getType() == NotificationType.RESERVATION_CANCELLATION){
                    enabled = owner.isCancellationNotificationEnabled();
                } else if (notification.getType() == NotificationType.OWNER_RATING){
                    enabled = owner.isRatingNotificationEnabled();
                } else if (notification.getType() == NotificationType.ACCOMMODATION_RATING){
                    enabled = owner.isAccNotificationEnabled();
                }
            }
            if(enabled) {
                return NotificationDTO.makeFromNotification(notification);
            }
        }
        return null;
    }
}
