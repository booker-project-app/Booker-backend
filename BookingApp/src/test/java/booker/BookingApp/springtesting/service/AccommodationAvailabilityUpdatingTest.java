package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.accommodation.CreatePriceDTO;
import booker.BookingApp.dto.accommodation.UpdateAvailabilityDTO;
import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.AvailabilityRepository;
import booker.BookingApp.repository.PriceRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.service.implementation.AccommodationService;
import booker.BookingApp.service.implementation.AvailabilityService;
import booker.BookingApp.service.implementation.PriceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AccommodationAvailabilityUpdatingTest {

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private PriceRepository priceRepository;

    @Captor
    private ArgumentCaptor<Accommodation> accommodationArgumentCaptor;
    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;

    @Autowired
    private AccommodationService accommodationService;

    @Test
    @DisplayName("should not update availability, accommodation is null")
    public void shouldNotUpdateAvailability_AccommodationIsNull() throws Exception {
        Long nonExistingAccommodationId = 999L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(159.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);


        Accommodation result = accommodationService.updateAvailability(nonExistingAccommodationId, updateAvailabilityDTO);


        assertNull(result);
        verify(accommodationRepository).findById(nonExistingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);


    }

    @Test
    @DisplayName("should return null, start date is in past")
    public void shouldNotUpdateAvailability_startDateInPast() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update availability, end date is in past")
    public void shouldNotUpdateAvailability_EndDateInPast() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, both dates are in the past")
    public void shouldNotUpdateAvailability_BothDatesInPast() throws Exception {
        Long existingAccommodationId = 1L;
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

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, end date is before start date")
    public void shouldNotUpdateAvailability_EndDateBeforeStartDate() throws Exception {
        Long existingAccommodationId = 1L;
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

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update availability, deadline is negative")
    public void shouldNotUpdateAvailability_NegativeDeadline() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(-1);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, cost is negative")
    public void shouldNotUpdateAvailability_NegativeCost() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(-100.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(1);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price start date is in past")
    public void shouldNotUpdateAvailability_PriceStartDateInPast() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(100.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_ACCOMMODATION);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price end date is in past")
    public void shouldNotUpdateAvailability_PriceEndDateInPast() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(100.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, both price dates are in past")
    public void shouldNotUpdateAvailability_BothPriceDatesInPast() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().minus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price end date is before start date")
    public void shouldNotUpdateAvailability_PriceEndDateBeforeStartDate() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(15, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, price type is null")
    public void shouldNotUpdateAvailability_PriceTypeNull() throws Exception {
        Long existingAccommodationId = 1L;
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(new Date());
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(null);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should not update availability, there are currently active reservations")
    public void shouldNotUpdateAvailability_hasCurrentlyActiveReservations() throws Exception {
        Long existingAccommodationId = 3L;
        String startDateString = "2024-03-21";
        String endDateString = "2024-03-24";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);
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

        when(reservationRepository.findCurrentlyActiveReservationsForAccommodation(existingAccommodationId, startDate, endDate)).thenReturn(
                Collections.singletonList(mock(Reservation.class))
        );



        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);

    }

    @Test
    @DisplayName("should not update reservation, price is null")
    public void shouldNotUpdateAvailability_PriceNull() throws Exception {
        Long existingAccommodationId = 1L;
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setDeadline(3);
        updateAvailabilityDTO.setPrice(null);

        assertNull(accommodationService.updateAvailability(existingAccommodationId, updateAvailabilityDTO));
        verify(accommodationRepository).findById(existingAccommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceRepository);
    }

    @Test
    @DisplayName("should update availability")
    public void shouldUpdateAvailability() throws Exception {
        Accommodation accommodation = new Accommodation(123L, "accommodation", "long description",
                "short description", 2L, new ArrayList<Amenity>(), new ArrayList<Image>(),
                new ArrayList<Availability>(), null, new ArrayList<Price>(), new ArrayList<AccommodationRating>(),
                new ArrayList<AccommodationComment>(), 3, 1, 10, AccommodationType.ROOM, true, false);

        when(accommodationRepository.save(Mockito.any(Accommodation.class))).thenReturn(accommodation);
        when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));


        Long accommodationId = 123L;
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

        Mockito.when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
        List<Availability> current = accommodation.getAvailabilities();
        System.out.println(current);
        List<Price> currentPrices = accommodation.getPrices();
        System.out.println(currentPrices);

        Accommodation result = accommodationService.updateAvailability(accommodationId, updateAvailabilityDTO);
        System.out.println(result);
        List<Availability> availabilities = accommodation.getAvailabilities();
        System.out.println(availabilities);
        List<Price> prices = result.getPrices();

        assertThat(result).isNotNull();
        assertThat(current.size() + 1).isEqualTo(availabilities.size());
        assertThat(currentPrices.size() + 1).isEqualTo(prices.size());
        verify(accommodationRepository, times(2)).save(accommodationArgumentCaptor.capture());


    }


}
