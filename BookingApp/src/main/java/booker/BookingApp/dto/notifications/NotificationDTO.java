package booker.BookingApp.dto.notifications;

import booker.BookingApp.model.notifications.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public @Data @AllArgsConstructor @NoArgsConstructor class NotificationDTO {
    private Long userId;
    private Date time;
    private String content;
    private String title;

    public NotificationDTO makeFromNotification(Notification notification){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.time = notification.getTime();
        notificationDTO.content = notification.getContent();
        notificationDTO.title = notification.getTitle();
        return notificationDTO;
    }

    public NotificationDTO(Long userId, String title){
        this.userId = userId;
        this.title = title;
    }

}
