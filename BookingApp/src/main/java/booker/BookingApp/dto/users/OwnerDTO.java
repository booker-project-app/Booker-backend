package booker.BookingApp.dto.users;

import booker.BookingApp.enums.Role;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO extends UserDTO {
    private ArrayList<OwnerRating> ratings;
    private ArrayList<OwnerComment> comments;

    public OwnerDTO(Long id, String name, String surname, String email, String address,
                    String phone, Role role, boolean deleted, ArrayList<OwnerRating> ratings, ArrayList<OwnerComment> comments){
        super(id, name, surname, email, address, phone, role, deleted);
        this.ratings = ratings;
        this.comments = comments;
    }
}
