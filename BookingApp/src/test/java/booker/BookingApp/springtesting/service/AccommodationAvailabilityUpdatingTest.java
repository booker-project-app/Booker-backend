package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.accommodation.*;
import booker.BookingApp.enums.*;
import booker.BookingApp.exceptions.*;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.*;
import booker.BookingApp.service.implementation.AccommodationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccommodationAvailabilityUpdatingTest {

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Accommodation> accommodationArgumentCaptor;
    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;

    @Autowired
    private AccommodationService accommodationService;

    @Test
    @DisplayName("should not update availability, accommodation is null")
    public void shouldNotUpdateAvailability_NonExistingAccommodation() throws Exception {
       User user = new User();
       user.setId(1L);
       user.setName("John");
       user.setSurname("Doe");
       user.setEmail("john@example.com");
       user.setPassword("12345678");
       user.setAddress("123 Main Street");
       user.setPhone("1234567890");
       user.setRole(Role.OWNER);
       user.setLastPasswordResetDate(null);
       user.setActivated(true);
       user.setActivationExpired(false);
       user.setActivationTimestamp(new Date());
       user.setProfilePicture(null);

       Accommodation accommodation = new Accommodation();
       accommodation.setId(999L);
       accommodation.setTitle("accommodation");
       accommodation.setDescription("long description");
       accommodation.setShortDescription("description");
       accommodation.setOwner_id(1L);
       accommodation.setAmenities(new ArrayList<Amenity>());
       accommodation.setImages(new ArrayList<Image>());
       accommodation.setAvailabilities(new ArrayList<Availability>());
       accommodation.setAddress(null);
       accommodation.setPrices(new ArrayList<Price>());
       accommodation.setRatings(new ArrayList<AccommodationRating>());
       accommodation.setComments(new ArrayList<AccommodationComment>());
       accommodation.setDeadline(0);
       accommodation.setMin_capacity(1);
       accommodation.setMax_capacity(10);
       accommodation.setType(AccommodationType.ROOM);
       accommodation.setAccepted(true);
       accommodation.setManual_accepting(false);

       CreatePriceDTO createPriceDTO = new CreatePriceDTO();
       createPriceDTO.setCost(150.0);
       createPriceDTO.setFromDate(new Date());
       createPriceDTO.setToDate(new Date());
       createPriceDTO.setType(PriceType.PER_GUEST);

       UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
       updateAvailabilityDTO.setStartDate(new Date());
       updateAvailabilityDTO.setEndDate(new Date());
       updateAvailabilityDTO.setPrice(createPriceDTO);
       updateAvailabilityDTO.setDeadline(3);

       Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
       Mockito.when(accommodationRepository.findById(999L)).thenReturn(Optional.empty());

        AccommodationUpdatedAvailabilityDTO result = accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO);



        assertNull(result);
        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository, priceRepository);


    }

    @Test
    @DisplayName("should return null, start date is in past")
    public void shouldNotUpdateAvailability_startDateInPast() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(1L);
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        accommodation.setAccepted(true);
        accommodation.setManual_accepting(false);

        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));

        assertThrows(StartDateInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update availability, end date is in past")
    public void shouldNotUpdateAvailability_EndDateInPast() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(1L);
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        accommodation.setAccepted(true);
        accommodation.setManual_accepting(false);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));

        assertThrows(EndDateInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, both dates are in the past")
    public void shouldNotUpdateAvailability_BothDatesInPast() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(1L);
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        accommodation.setAccepted(true);
        accommodation.setManual_accepting(false);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().minus(35, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));

        assertThrows(BothDatesInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, end date is before start date")
    public void shouldNotUpdateAvailability_EndDateBeforeStartDate() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(1L);
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        accommodation.setAccepted(true);
        accommodation.setManual_accepting(false);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(EndDateBeforeStartDateException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update availability, deadline is negative")
    public void shouldNotUpdateAvailability_NegativeDeadline() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(-1);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(NegativeDeadlineException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, cost is negative")
    public void shouldNotUpdateAvailability_NegativeCost() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(-100.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(1);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(NegativeCostException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price start date is in past")
    public void shouldNotUpdateAvailability_PriceStartDateInPast() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(100.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_ACCOMMODATION);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(StartDateInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price end date is in past")
    public void shouldNotUpdateAvailability_PriceEndDateInPast() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(100.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(EndDateInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, both price dates are in past")
    public void shouldNotUpdateAvailability_BothPriceDatesInPast() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().minus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(BothDatesInPastException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));


        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price end date is before start date")
    public void shouldNotUpdateAvailability_PriceEndDateBeforeStartDate() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(EndDateBeforeStartDateException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price type is null")
    public void shouldNotUpdateAvailability_PriceTypeNull() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        createPriceDTO.setType(null);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(PriceTypeNullException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));


        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, there are currently active reservations")
    public void shouldNotUpdateAvailability_hasCurrentlyActiveReservations() throws Exception {
        String startDateString = "2024-03-21";
        String endDateString = "2024-03-24";
        String reservationEndDateString = "2024-03-26";
        String reservationStartDateString = "2024-03-19";
        String reservationToTimeString = "2024-03-26 17:45:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);

        User user = new User();
        user.setId(3L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(3L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(3L)).thenReturn(Optional.of(accommodation));

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuestId(user.getId());
        reservation.setAccommodation(accommodation);
        reservation.setFromDate(reservationStartDateString);
        reservation.setToDate(reservationEndDateString);
        reservation.setNumberOfGuests(4);
        reservation.setRequestStatus(ReservationRequestStatus.ACCEPTED);
        reservation.setStatus(ReservationStatus.ACCEPTED);
        reservation.setDeleted(false);
        reservation.setPrice(200.0);
        reservation.setToTime(reservationToTimeString);

        Mockito.when(reservationRepository.findCurrentlyActiveReservationsForAccommodation(3L, startDate, endDate))
                .thenReturn(Collections.singletonList(reservation));


        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(startDate);
        createPriceDTO.setToDate(endDate);
        createPriceDTO.setType(PriceType.PER_GUEST);



        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(startDate);
        updateAvailabilityDTO.setEndDate(endDate);
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);





        assertThrows(HasCurrentlyActiveReservationsException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));


        verify(accommodationRepository).findById(accommodation.getId());
        verify(reservationRepository).findCurrentlyActiveReservationsForAccommodation(3L, startDate, endDate);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update availability, price is null")
    public void shouldNotUpdateAvailability_PriceNull() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setDeadline(3);
        updateAvailabilityDTO.setPrice(null);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        assertThrows(PriceNullException.class, () -> accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO));

        verify(accommodationRepository).findById(accommodation.getId());
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should update availability")
    public void shouldUpdateAvailability() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345678");
        user.setAddress("123 Main Street");
        user.setPhone("1234567890");
        user.setRole(Role.OWNER);
        user.setLastPasswordResetDate(null);
        user.setActivated(true);
        user.setActivationExpired(false);
        user.setActivationTimestamp(new Date());
        user.setProfilePicture(null);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setTitle("accommodation");
        accommodation.setDescription("long description");
        accommodation.setShortDescription("description");
        accommodation.setOwner_id(user.getId());
        accommodation.setAmenities(new ArrayList<Amenity>());
        accommodation.setImages(new ArrayList<Image>());
        accommodation.setAvailabilities(new ArrayList<Availability>());
        accommodation.setAddress(null);
        accommodation.setPrices(new ArrayList<Price>());
        accommodation.setRatings(new ArrayList<AccommodationRating>());
        accommodation.setComments(new ArrayList<AccommodationComment>());
        accommodation.setDeadline(0);
        accommodation.setMin_capacity(1);
        accommodation.setMax_capacity(10);
        accommodation.setType(AccommodationType.ROOM);

        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        List<Availability> current = accommodation.getAvailabilities();
        System.out.println(current);
        List<Price> currentPrices = accommodation.getPrices();
        System.out.println(currentPrices);

        AccommodationUpdatedAvailabilityDTO result = accommodationService.updateAvailability(accommodation.getId(), updateAvailabilityDTO);
        System.out.println(result);
        List<Availability> availabilities = accommodation.getAvailabilities();
        System.out.println(availabilities);
        List<Price> prices = result.getPrices();

        assertThat(result).isNotNull();
        verify(accommodationRepository).findById(accommodation.getId());
        assertThat(current.size() + 1).isEqualTo(availabilities.size());
        assertThat(currentPrices.size() + 1).isEqualTo(prices.size());



    }


}
