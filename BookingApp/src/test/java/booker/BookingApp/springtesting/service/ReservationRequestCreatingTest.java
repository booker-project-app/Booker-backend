package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.ReservationRequestRepository;
import booker.BookingApp.service.implementation.AccommodationService;
import booker.BookingApp.service.implementation.AvailabilityService;
import booker.BookingApp.service.implementation.ReservationRequestService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReservationRequestCreatingTest {
    @Mock
    ReservationRequestRepository repository;

    @Mock
    AvailabilityService availabilityService;

    @Mock
    AccommodationService accommodationService;

    @InjectMocks
    ReservationRequestService service;

    @BeforeEach
    public void initialize() throws ParseException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should not parse date")
    public void dateNotValid_returnsNull() throws IOException {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "lalala", "aaa", 2,
                ReservationRequestStatus.WAITING, false, 250);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        assertNull(service.create(requestDTO));
        verify(accommodationService).findOne(1L);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should not find accommodation, it does not exist")
    public void accommodationNotAvailable_returnsNull() throws IOException, ParseException {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-03-05");
        Date endDate = sdf.parse("2024-03-15");
        when(availabilityService.checkIfAvailable(1L, startDate, endDate)).thenReturn(false);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        assertNull(service.create(requestDTO));
        verify(availabilityService).checkIfAvailable(1L, startDate, endDate);
        verify(accommodationService).findOne(1L);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should return null, start date is in the past")
    public void dateValidation_startDateInPast_returnsNull() throws IOException, ParseException {
        ReservationRequestDTO invalidRequestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2023-01-05", "2023-01-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2023-01-05");
        Date end = sdf.parse("2023-01-15");
        when(availabilityService.checkIfAvailable(1L, start, end)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        assertNull(service.create(invalidRequestDTO));
        verify(availabilityService).checkIfAvailable(1L, start, end);
        verify(accommodationService).findOne(1L);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should return null, start date is after end date")
    public void dateValidation_endDateBeforeStartDate_returnsNull() throws ParseException, IOException {
        ReservationRequestDTO invalidRequestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-02-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2024-03-05");
        Date end = sdf.parse("2024-02-15");
        when(availabilityService.checkIfAvailable(1L, start, end)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        assertNull(service.create(invalidRequestDTO));
        verify(availabilityService).checkIfAvailable(1L, start, end);
        verify(accommodationService).findOne(1L);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should return null, price is not good")
    public void priceValidation_priceNotGood_returnsNull() throws ParseException, IOException {
        ReservationRequestDTO invalidRequestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2024-03-05");
        Date end = sdf.parse("2024-03-15");
        when(availabilityService.checkIfAvailable(1L, start, end)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        when(accommodationService.findPriceForDateRange(1L, start, end, 2)).thenReturn(200d);
        assertNull(service.create(invalidRequestDTO));
        verify(availabilityService).checkIfAvailable(1L, start, end);
        verify(accommodationService).findOne(1L);
        verify(accommodationService).findPriceForDateRange(1L, start, end, 2);
        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(repository);
    }

    @ParameterizedTest
    @ValueSource(ints={-3, 0, 1, 4, 9})
    @DisplayName("Should return null, number of guests is not good")
    public void numOfGuestsNotGood_returnsNull(int numOfPeople) throws ParseException, IOException {
        ReservationRequestDTO invalidRequestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", numOfPeople,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2024-03-05");
        Date end = sdf.parse("2024-03-15");
        when(availabilityService.checkIfAvailable(1L, start, end)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        when(accommodationService.findPriceForDateRange(1L, start, end, numOfPeople)).thenReturn(250d);
        assertNull(service.create(invalidRequestDTO));
        verify(availabilityService).checkIfAvailable(1L, start, end);
        verify(accommodationService, times(2)).findOne(1L);
        verify(accommodationService).findPriceForDateRange(1L, start, end, numOfPeople);
        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should create request")
    public void positiveTest_returnsReservationRequestDto() throws IOException, ParseException {
        ReservationRequestDTO validRequestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2024-03-05");
        Date end = sdf.parse("2024-03-15");
        when(availabilityService.checkIfAvailable(1L, start, end)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(1L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, true, 3);
        when(accommodationService.findOne(1L)).thenReturn(accommodation);
        when(accommodationService.findPriceForDateRange(1L, start, end, 2)).thenReturn(250d);
        assertEquals(validRequestDTO, service.create(validRequestDTO));
        verify(availabilityService).checkIfAvailable(1L, start, end);
        verify(accommodationService, times(2)).findOne(1L);
        verify(accommodationService).findPriceForDateRange(1L, start, end, 2);
        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verify(repository, times(1)).save(any());
    }

}
