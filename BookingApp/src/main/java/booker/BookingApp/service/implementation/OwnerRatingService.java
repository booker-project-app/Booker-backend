package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerRatingDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerRatingDTO;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.repository.OwnerRatingRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IOwnerRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OwnerRatingService implements IOwnerRatingService {
    @Autowired
    private OwnerRatingRepository ownerRatingRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public OwnerRating findOne(Long id) {
        return ownerRatingRepository.findById(id).orElse(null);
    }

    @Override
    public List<OwnerRating> findAll() {
        return ownerRatingRepository.findAll();
    }
    @Override
    public List<OwnerRating> findAllReported() {
        return ownerRatingRepository.findAllReported();
    }
    @Override
    public void delete(Long id) {
        OwnerRating rating = ownerRatingRepository.findById(id).orElseGet(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Guest user) {

                rating.setDeleted(true);
                ownerRatingRepository.save(rating);
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }

    }

    @Override
    public OwnerRatingDTO create(CreateOwnerRatingDTO createOwnerRatingDTO) {
        OwnerRating rating = new OwnerRating();
        rating.setRate(createOwnerRatingDTO.getRate());
        rating.setReported(false);
        rating.setDate(new Date());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Guest user) {
                List<Reservation> reservations = reservationRepository.findAllForGuest(user.getId(), createOwnerRatingDTO.getOwnerId());
                if (reservations.size() == 0) {
                    throw new RuntimeException("The guest has no uncancelled reservations. Rating is not allowed.");

                }

                rating.setGuest(user);
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }

        Owner owner = (Owner) userRepository.findById(createOwnerRatingDTO.getOwnerId()).orElseGet(null);
        rating.setOwner(owner);
        ownerRatingRepository.save(rating);
        OwnerRatingDTO ownerRatingDTO = OwnerRatingDTO.makeFromOwnerRating(rating);
        return ownerRatingDTO;
    }
//    @Override
//    public OwnerRating save(OwnerRating ownerRating) {
//        return ownerRatingRepository.save(ownerRating);
//    }

    @Override
    public OwnerRatingDTO update(OwnerRatingDTO ownerRatingDTO) {
        return ownerRatingDTO;
    }

    @Override
    public List<OwnerRating> getAllForOwner(Long ownerId) {
        return ownerRatingRepository.findAllForOwner(ownerId);
    }

    @Override
    public void report(Long id) {
        OwnerRating rating = ownerRatingRepository.findById(id).orElseGet(null);
        rating.setReported(true);
        ownerRatingRepository.save(rating);
    }

    @Override
    public void deleteForAdmin(Long id) {
        OwnerRating rating = ownerRatingRepository.findById(id).get();
        rating.setDeleted(true);
        ownerRatingRepository.save(rating);
    }


}
