package booker.BookingApp.dto.users;

import booker.BookingApp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO extends UserDTO{
    private boolean blocked;
    private ArrayList<Long> favouriteAccommodations;

    public GuestDTO(Long id, String name, String surname, String email, String address, String phone,
                    Role role, boolean blocked, boolean deleted, ArrayList<Long> favouriteAccommodations){
        super(id, name, surname, email, address, phone, role, deleted);
        this.blocked = blocked;
        this.favouriteAccommodations = favouriteAccommodations;
    }
}
