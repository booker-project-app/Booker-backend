package booker.BookingApp.model.users;

import booker.BookingApp.enums.Role;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(exclude = {"ratings", "comments"})
@DiscriminatorValue("OWNER")
public class Owner extends User {
    @Column(name = "reported")
    private boolean reported;
    @Column(name = "blocked")
    private boolean blocked;
    @Column(name = "deleted")
    private boolean deleted;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OwnerRating> ratings;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OwnerComment> comments;
    @Column(name = "requestNotificationEnabled")
    private boolean requestNotificationEnabled;
    @Column(name = "cancellationNotificationEnabled")
    private boolean cancellationNotificationEnabled;
    @Column(name = "ratingNotificationEnabled")
    private boolean ratingNotificationEnabled;
    @Column(name = "accNotificationEnabled")
    private boolean accNotificationEnabled;
}
