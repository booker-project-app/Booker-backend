package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.model.users.Guest;

import java.util.ArrayList;

public interface IGuestService {
    ArrayList<GuestDTO> findAll();
    GuestDTO getGuestById(Long guestId);
    GuestDTO getGuestByEmail(String email);
    GuestDTO insert(GuestDTO guest);
    GuestDTO update(GuestDTO guest);
    void delete(Long guestId);
    ArrayList<Long> addToFavouriteAccommodations(GuestDTO guest, Long accommodationId);
    ArrayList<Long> removeFromFavouriteAccommodations(GuestDTO guest, Long accommodationId);
}
