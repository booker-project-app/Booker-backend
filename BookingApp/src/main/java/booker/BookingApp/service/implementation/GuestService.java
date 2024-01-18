package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.FavouriteAccommodationDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.dto.users.OwnerDTO;
import booker.BookingApp.dto.users.UpdateUserDTO;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.ProfilePicture;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService implements IGuestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccommodationService accommodationService;

    @Override
    public ArrayList<GuestDTO> findAll() {
        List<Guest> guests = userRepository.getAllGuests();
        ArrayList<GuestDTO> dtos = new ArrayList<>();
        for (Guest g : guests){
            dtos.add(GuestDTO.makeFromGuest(g));
        }
        return dtos;
    }

    @Override
    public GuestDTO getGuestById(Long id) {
        Guest g = (Guest) userRepository.getOne(id);
        return GuestDTO.makeFromGuest(g);
    }

    @Override
    public GuestDTO getGuestByEmail(String email) {
        ArrayList<Long> faves = new ArrayList<>();
        faves.add(1L); faves.add(2L);
        ProfilePicture profilePicture = new ProfilePicture(1L, "src/main/resources/images/profile1.png", "", new User());
        return null;
    }

    @Override
    public GuestDTO insert(GuestDTO guest) {
        return guest;
    }

    @Override
    public GuestDTO update(GuestDTO guest, UpdateUserDTO updateUser) {
        Guest g = (Guest) userRepository.getOne(guest.getId());
        if (g != null){
            g.setName(updateUser.getName());
            g.setSurname(updateUser.getSurname());
            g.setEmail(updateUser.getEmail());
            g.setAddress(updateUser.getAddress());
            g.setPhone(updateUser.getPhone());
            g.setProfilePicturePath(updateUser.getProfilePicture().getPath());
            g.setPassword(updateUser.getPassword());
            userRepository.save(g);
            return GuestDTO.makeFromGuest(g);
        }

        return guest;
    }

    @Override
    public boolean checkForDeletion(Long guestId){
        // get all active reservations for guest
        // if reservations.length == 0 return true
        // else
        return false;
    }

    @Override
    public Boolean delete(GuestDTO guest) {
        if(checkForDeletion(guest.getId())){
            Guest deletedGuest = (Guest) userRepository.delete(guest.getId());
            // TODO log out user
            return true;
        }
        return false;
    }

    @Override
    public void block(Long id) {
    }

    @Override
    public OwnerDTO reportOwner(String ownerEmail) {
        ProfilePicture profilePicture = new ProfilePicture(1L, "src/main/resources/images/profile1.png", "", new User());
        return null;
    }

    @Override
    public ArrayList<GuestDTO> getAllBlocked() {
        ArrayList<GuestDTO> all = findAll();
        ArrayList<GuestDTO> blocked = new ArrayList<>();
        for (GuestDTO g : all){
            if (g.isBlocked()){
                blocked.add(g);
            }
        }
        return blocked;
    }

    @Override
    public ArrayList<GuestDTO> getAllReported() {
        ArrayList<GuestDTO> all = findAll();
        ArrayList<GuestDTO> reported = new ArrayList<>();
        for (GuestDTO g : all){
            if (g.isReported()){
                reported.add(g);
            }
        }
        return reported;
    }

    @Override
    public boolean addToFavouriteAccommodations(Long guestId, Long accommodationId) {
        Guest guest = (Guest) userRepository.findById(guestId).get();
        ArrayList<Long> favourites = guest.getFavouriteAccommodations();
        favourites.add(accommodationId);
        guest.setFavouriteAccommodations(favourites);
        this.userRepository.save(guest);
        return true;
    }

    @Override
    public boolean removeFromFavouriteAccommodations(Long guestId, Long accommodationId) {
        Guest guest = (Guest) userRepository.findById(guestId).get();
        ArrayList<Long> favourites = guest.getFavouriteAccommodations();
        favourites.remove(accommodationId);
        guest.setFavouriteAccommodations(favourites);
        this.userRepository.save(guest);
        return true;
    }

    @Override
    public ArrayList<FavouriteAccommodationDTO> findAllFavouriteAccommodations(GuestDTO guest) throws IOException {
        ArrayList<Long> favouriteIds = guest.getFavouriteAccommodations();
        ArrayList<FavouriteAccommodationDTO> favourites = new ArrayList<>();
        for(Long id : favouriteIds){
            AccommodationViewDTO acc = accommodationService.findOne(id);
            favourites.add(FavouriteAccommodationDTO.makeFromAccommodationView(acc));
        }
        return favourites;
    }

    @Override
    public boolean isFavourite(Long guestId, Long accId) {
        GuestDTO guestDTO = getGuestById(guestId);
        ArrayList<Long> favourites = guestDTO.getFavouriteAccommodations();
        return favourites.contains(accId);
    }

    @Override
    public int findNumOfCancellations(Long guestId) {
        return userRepository.getNumOfCancellations(guestId);
    }

    @Override
    public void updateSettings(GuestDTO guestDTO, boolean checked) {
        Guest guest = (Guest) userRepository.findById(guestDTO.getId()).get();
        guest.setNotificationEnabled(checked);
        userRepository.save(guest);
    }


}
