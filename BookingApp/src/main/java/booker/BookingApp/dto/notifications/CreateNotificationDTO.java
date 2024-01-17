package booker.BookingApp.dto.notifications;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import booker.BookingApp.model.notifications.Notification;
import lombok.Data;

import java.util.Date;
public @Data class CreateNotificationDTO {
    private Long userId;
    private String content;
    private String title;

    public CreateNotificationDTO() {
    }

    public CreateNotificationDTO(Notification notification) {
        this(notification.getUser().getId(), notification.getContent(), notification.getTitle());
    }

    public CreateNotificationDTO(Long userId, String content, String title) {
        this.userId = userId;
        this.content = content;
        this.title = title;
    }


}
