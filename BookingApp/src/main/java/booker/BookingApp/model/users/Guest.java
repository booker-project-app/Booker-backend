package booker.BookingApp.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
//@Table(name = "GUESTS")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("GUEST")
public class Guest extends User {
    @Column(name = "reported")
    private boolean reported;
    @Column(name = "blocked")
    private boolean blocked;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "favourite_accommodations")
    private ArrayList<Long> favouriteAccommodations;
    @Column(name = "notificationEnabled")
    private boolean notificationEnabled;

}
