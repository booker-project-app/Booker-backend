package booker.BookingApp.model.accommodation;

import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
public @Data @NoArgsConstructor class AccommodationRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @Column(name = "rate", nullable = false)
    private float rate;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "reported", nullable = false)
    private boolean reported;
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
