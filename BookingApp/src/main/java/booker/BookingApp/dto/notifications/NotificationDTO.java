package booker.BookingApp.dto.notifications;

import booker.BookingApp.enums.NotificationType;
import booker.BookingApp.model.notifications.Notification;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public @Data @AllArgsConstructor @NoArgsConstructor class NotificationDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotEmpty
    private String time;
    @NotEmpty
    private String content;
    @NotNull
    private NotificationType type;

    public static NotificationDTO makeFromNotification(Notification notification){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.id = notification.getId();
        notificationDTO.userId = notification.getUserId();
        notificationDTO.time = notification.getTime();
        notificationDTO.content = notification.getContent();
        notificationDTO.type = notification.getType();
        return notificationDTO;
    }

}
