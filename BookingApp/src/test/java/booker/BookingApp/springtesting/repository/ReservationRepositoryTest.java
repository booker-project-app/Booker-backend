package booker.BookingApp.springtesting.repository;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.enums.ReservationStatus;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.ProfilePicture;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    public void setUp() {
        User owner = new User();
        owner.setId(1L);
        owner.setName("John");
        owner.setSurname("Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPassword("12345678"); // You should use a hashed password in a real scenario
        owner.setAddress("123 Main Street");
        owner.setPhone("1234567890");
        owner.setRole(Role.OWNER);
        owner.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        owner.setActivationLink("activationLink"); // Replace with actual activation logic
        owner.setActivated(true);
        owner.setActivationExpired(false);
        owner.setActivationTimestamp(new Date());

// Create a profile picture and associate it with the user
        ProfilePicture ownerProfilePicture = new ProfilePicture();
        ownerProfilePicture.setUser(owner);
        ownerProfilePicture.setPath("../../assets/images/profile_pic.jpg");
        // Replace with the actual path
        ownerProfilePicture.setPath_mobile("../../../../../res/drawable/profile_pic.jpg");
        owner.setProfilePicture(ownerProfilePicture);

        User guest = new User();
        guest.setId(2L);
        guest.setName("Alice");
        guest.setSurname("Smith");
        guest.setEmail("alice.smith@example.com");
        guest.setPassword("123456789"); // Use a proper password hashing method in a real application
        guest.setAddress("456 Oak Street");
        guest.setPhone("9876543210");
        guest.setRole(Role.GUEST); // Assuming a different role for this user
        guest.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        guest.setActivationLink("activationLink2");
        guest.setActivated(true);
        guest.setActivationExpired(false);
        guest.setActivationTimestamp(new Date());

        ProfilePicture guestProfilePicture = new ProfilePicture();
        guestProfilePicture.setUser(guest);
        guestProfilePicture.setPath("../../assets/images/profile_pic.jpg");
        guestProfilePicture.setPath_mobile("../../../../../res/drawable/profile_pic.jpg");
        guest.setProfilePicture(guestProfilePicture);

        userRepository.save(owner);
        userRepository.save(guest);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(15L);
        accommodation.setTitle("Cozy Cottage");
        accommodation.setDescription("A charming cottage in the countryside");
        accommodation.setShortDescription("Perfect for a peaceful getaway");
        accommodation.setOwner_id(101L); // Replace with the actual owner ID
        accommodation.setAmenities(null); // Add amenities as needed
        accommodation.setImages(null); // Add images as needed
        accommodation.setAvailabilities(null); // Add availabilities as needed
        accommodation.setAddress(null);
        accommodation.setPrices(null); // Add prices as needed
        accommodation.setRatings(null); // Add ratings as needed
        accommodation.setComments(null); // Add comments as needed
        accommodation.setDeadline(7); // Replace with the actual deadline
        accommodation.setMin_capacity(2);
        accommodation.setMax_capacity(4);
        accommodation.setType(AccommodationType.HOTEL); // Replace with the actual type
        accommodation.setAccepted(true);
        accommodation.setManual_accepting(false);

        accommodationRepository.save(accommodation);

        Date currentDate = new Date();
        String currentDateStr = currentDate.toString();

// Create a reservation object
        Reservation reservation = new Reservation();
        reservation.setGuestId(guest.getId()); // Replace with actual guest ID
        reservation.setAccommodation(accommodation); // Replace with actual accommodation
        reservation.setFromDate(currentDateStr); // Replace with actual start date
        reservation.setToDate(currentDateStr); // Replace with actual end date
        reservation.setNumberOfGuests(2); // Replace with actual number of guests
        reservation.setRequestStatus(ReservationRequestStatus.ACCEPTED); // Set the request status
        reservation.setStatus(ReservationStatus.ACCEPTED); // Set the reservation status
        reservation.setDeleted(false); // Set deleted status
        reservation.setPrice(100.0); // Replace with actual price

        reservationRepository.save(reservation);

    }


    @Test
    public void shouldFindCurrentReservations() {
        Date date = new Date();
        List<Reservation> reservations = reservationRepository.findCurrentlyActiveReservationsForAccommodation(1L, date, date);
        assertFalse(reservations.isEmpty());

    }
}
