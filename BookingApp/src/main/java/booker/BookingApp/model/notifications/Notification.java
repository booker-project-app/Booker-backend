package booker.BookingApp.model.notifications;

import booker.BookingApp.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
public @Data @AllArgsConstructor @NoArgsConstructor class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "time")
    private Date time;
    @Column(name = "content")
    private String content;
    @Column(name = "title")
    private String title;
}
