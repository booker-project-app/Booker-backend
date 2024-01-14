package booker.BookingApp.model.accommodation;

import booker.BookingApp.model.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
public @Data class AccommodationComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private User user;

    @Column(name = "content", nullable = false) // unique = true
    private String content;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "reported", nullable = false)
    private boolean reported;
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
    @Column(name = "approved", nullable = false)
    private boolean approved;
}
