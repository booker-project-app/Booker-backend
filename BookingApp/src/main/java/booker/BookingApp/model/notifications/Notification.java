package booker.BookingApp.model.notifications;

import booker.BookingApp.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
public @Data @AllArgsConstructor @NoArgsConstructor class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @Column(name = "user_id", nullable = false)
    @NotNull
    private Long userId;
    @Column(name = "time", nullable = false)
    @NotEmpty
    private String time;
    @Column(name = "content", nullable = false)
    @NotEmpty
    private String content;
    @Column(name = "type", nullable = false)
    @NotNull
    private NotificationType type;
}
