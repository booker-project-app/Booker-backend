package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.FavouriteAccommodationDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.dto.accommodation.AccommodationListingDTO;
import booker.BookingApp.dto.users.OwnerDTO;
import booker.BookingApp.dto.users.UpdateUserDTO;

import java.io.IOException;
import java.util.ArrayList;

public interface IGuestService {
    ArrayList<GuestDTO> findAll();
    GuestDTO getGuestById(Long guestId);
    GuestDTO getGuestByEmail(String email);
    GuestDTO insert(GuestDTO guest);
    GuestDTO update(GuestDTO guest, UpdateUserDTO updateUserDTO);
    boolean checkForDeletion(Long guestId);
    Boolean delete(GuestDTO guest);
    void block(Long guestId);
    OwnerDTO reportOwner(String ownerEmail);
    ArrayList<GuestDTO> getAllBlocked();
    ArrayList<GuestDTO> getAllReported();
    boolean addToFavouriteAccommodations(Long guestId, Long accommodationId);
    boolean removeFromFavouriteAccommodations(Long guestId, Long accommodationId);
    ArrayList<FavouriteAccommodationDTO> findAllFavouriteAccommodations(GuestDTO guest) throws IOException;

    boolean isFavourite(Long guestId, Long accId);

    int findNumOfCancellations(Long guestId);

    void updateSettings(GuestDTO guest, boolean checked);
}
