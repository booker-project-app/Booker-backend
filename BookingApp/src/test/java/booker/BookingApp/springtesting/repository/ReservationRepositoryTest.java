package booker.BookingApp.springtesting.repository;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.enums.ReservationStatus;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.ProfilePicture;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

//    @Value("${spring.datasource.url}")
//    private String dataSourceUrl;

//    @Test
//    public void checkDataSourceUrl() {
//        System.out.println("DataSource URL: " + dataSourceUrl);
//    }


    @Test
    @DisplayName("should find all past not cancelled reservations for guest and owner")
    public void shouldFindAllReservationsForGuest() {
//        Accommodation accommodation = new Accommodation(null, "new accommodation", "new acc", "new", 2L,
//                new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<Availability>(), null,
//                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
//                3, 1, 10, AccommodationType.HOTEL, true, false);
//        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
//        Reservation reservation = new Reservation(null, 1L, savedAccommodation, "2024-01-10",
//                "2024-01-15", 2, ReservationRequestStatus.ACCEPTED,
//                ReservationStatus.ACCEPTED, false, 150.0, "2024-01-15 12:00:00");
//        Reservation savedReservation = reservationRepository.save(reservation);
//        List<Reservation> result = reservationRepository.findAllForGuest(1L, 2L);
//        assertThat(savedAccommodation).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodation);
//        assertThat(savedReservation).usingRecursiveComparison().ignoringFields("id").isEqualTo(reservation);
        List<Reservation> result = reservationRepository.findAllForGuest(1L, 2L);
        assertThat(result).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("should not find reservations for guest and owner because guest doesn't exist")
    public void shouldNotFindReservationsForGuest_GuestNotExist() {
        Long nonExistingGuest = 999L;

        List<Reservation> result = reservationRepository.findAllForGuest(nonExistingGuest, 2L);

        assertThat(result).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("should not find reservations for guest and owner because owner doesn't exist")
    public void shouldNotFindReservationsForGuest_OwnerNotFound() {
        Long nonExistingOwner = 999L;

        List<Reservation> reservations = reservationRepository.findAllForGuest(1L, nonExistingOwner);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find reservations for guest and owner because owner doesn't exist")
    public void shouldNotFindReservationsForGuestAndOwner_GuestAndOwnerNotExist() {
        Long nonExistingGuest = 999L;
        Long nonExistingOwner = 998L;

        List<Reservation> reservations = reservationRepository.findAllForGuest(nonExistingGuest, nonExistingOwner);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should find all reservations for guest in accommodation")
    public void shouldFindAllReservationsForGuestInAccommodation() {
        List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(1L, 1L);
        assertThat(reservations).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("should not find all reservations for guest in accommodation, guest doesn't exist")
    public void shouldNotFindAllReservationsForGuestInAccommodation_GuestNotFound() {
        Long nonExistentGuestId = 999L;

        List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(nonExistentGuestId, 1L);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find all reservations for guest in accommodation, accommodation doesn't exist")
    public void shouldNotFindAllReservationsForGuestInAccommodation_AccommodationNotFound() {
        Long nonExistingAccommodationId = 999L;

        List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(1L, nonExistingAccommodationId);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find all reservations for guest in accommodation, guest and accommodation don't exist")
    public void shouldNotFindAllReservationsForGuestInAccommodation_GuestAndAccommodationNotFound() {
        Long nonExistingGuestId = 999L;
        Long nonExistingAccommodationId = 999L;

        List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(nonExistingGuestId, nonExistingAccommodationId);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should find all past reservations for guest and owner")
    public void shouldFindAllPastForGuest() {
        List<Reservation> reservations = reservationRepository.findAllPastForGuest(1L, 2L);

        assertThat(reservations).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("should not find all past reservations for guest and owner, guest doesn't exist")
    public void shouldNotFindAllPastForGuest_GuestNotFound() {
        Long nonExistingGuestId = 999L;

        List<Reservation> reservations = reservationRepository.findAllPastForGuest(nonExistingGuestId, 2L);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find all past reservations for guest and owner, owner doesn't exist")
    public void shouldNotFindAllPastForGuest_OwnerNotFound() {
        Long nonExistingOwnerId = 999L;

        List<Reservation> reservations = reservationRepository.findAllPastForGuest(1L, nonExistingOwnerId);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find all past reservations for guest and owner, owner and guest don't exist")
    public void shouldNotFindAllPastForGuest_OwnerAndGuestNotFound() {
        Long nonExistingOwnerId = 999L;
        Long nonExistingGuestId = 999L;

        List<Reservation> reservations = reservationRepository.findAllPastForGuest(nonExistingGuestId, nonExistingOwnerId);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should find currently active reservations for accommodation")
    public void shouldFindCurrentlyActiveReservationsForAccommodation() throws ParseException {
        Long accommodationId = 3L;
        String startDateString = "2024-03-21";
        String endDateString = "2024-03-24";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);

        List<Reservation> reservations = reservationRepository.findCurrentlyActiveReservationsForAccommodation(accommodationId, startDate, endDate);
        System.out.println("StartDate = " + startDate);
        System.out.println("EndDate = " + endDate);
        System.out.println("accommodationId = " + accommodationId);
        System.out.println("Number of Reservations: " + reservations.size());
        System.out.println("Reservations: " + reservations);


        assertThat(reservations).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("should not find currently active reservations outside date range")
    public void shouldNotFindCurrentlyActiveReservationsOutsideDateRange() throws ParseException {
        Long accommodationId = 1L;
        String startDateString = "2023-01-06";
        String endDateString = "2023-01-06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);

        List<Reservation> reservations = reservationRepository.findCurrentlyActiveReservationsForAccommodation(accommodationId, startDate, endDate);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should not find reservations for non-existing accommodation")
    public void shouldNotFindCurrentlyActiveReservationsForNonExistingAccommodation() throws ParseException {
        Long accommodationId = 999L;
        String startDateString = "2024-01-01";
        String endDateString = "2024-01-05";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);

        List<Reservation> reservations = reservationRepository.findCurrentlyActiveReservationsForAccommodation(accommodationId, startDate, endDate);

        assertThat(reservations).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should find last past reservation for guest in accommodation")
    public void shouldFindLastPastReservationForGuestInAccommodation() {
        Long guestId = 1L;
        Long accommodationId = 1L;

        Reservation reservation = reservationRepository.findLastPastReservationForGuestInAccommodation(guestId, accommodationId);

        assertThat(reservation).isNotNull();
        assertEquals(reservationRepository.findLastPastReservationForGuestInAccommodation(guestId, accommodationId).getStatus().name(),
                "ACCEPTED");
    }

    @Test
    @DisplayName("should not find last past reservation for non-existing guest")
    public void shouldNotFindLastPastReservationForGuestInAccommodationForNonExistingGuest() {
        Long guestId = 999L;
        Long accommodationId = 1L;

        Reservation reservation = reservationRepository.findLastPastReservationForGuestInAccommodation(guestId, accommodationId);

        assertThat(reservation).isNull();
    }

    @Test
    @DisplayName("should not find last past reservation for non-existing accommodation")
    public void shouldNotFindLastPastReservationForGuestInAccommodationForNonExistingAccommodation() {
        Long guestId = 1L;
        Long accommodationId = 123L;

        Reservation reservation = reservationRepository.findLastPastReservationForGuestInAccommodation(guestId, accommodationId);

        assertThat(reservation).isNull();
    }

    @Test
    @DisplayName("should not find last past reservation for non-existing accommodation and guest")
    public void shouldNotFindLastPastReservationForGuestInAccommodationForNonExistingGuestAndAccommodation() {
        Long guestId = 999L;
        Long accommodationId = 999L;

        Reservation reservation = reservationRepository.findLastPastReservationForGuestInAccommodation(guestId, accommodationId);

        assertThat(reservation).isNull();
    }
}
