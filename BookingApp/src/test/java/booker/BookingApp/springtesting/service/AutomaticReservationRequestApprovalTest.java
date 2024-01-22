package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.ReservationRequestRepository;
import booker.BookingApp.service.implementation.AccommodationService;
import booker.BookingApp.service.implementation.AvailabilityService;
import booker.BookingApp.service.implementation.ReservationRequestService;
import booker.BookingApp.service.implementation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AutomaticReservationRequestApprovalTest {
    @Mock
    ReservationRequestRepository requestRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    AvailabilityService availabilityService;

    @Mock
    AccommodationService accommodationService;

    @Mock
    ReservationService reservationService;

    @InjectMocks
    ReservationRequestService service;

    @BeforeEach
    public void initialize() throws ParseException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return null, accommodation is not available")
    public void TestAccommodationNotAvailable_returnsNull() throws IOException, ParseException {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 2L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-03-05");
        Date endDate = sdf.parse("2024-03-15");
        when(availabilityService.checkForDateRange(2L, startDate, endDate)).thenReturn(false);
        assertNull(service.create(requestDTO));
        verify(availabilityService).checkForDateRange(2L, startDate, endDate);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(reservationService);
        verifyNoInteractions(requestRepository);
        verifyNoInteractions(reservationRepository);
    }

    @Test
    @DisplayName("Should return request, create reservation request and reservation")
    public void TestApproveReservationRequestAndCreateReservation_success() throws IOException, ParseException {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 2L,
                1L, "2024-02-21", "2024-02-24", 2,
                ReservationRequestStatus.WAITING, false, 250);
        ReservationRequestDTO approvedRequest = new ReservationRequestDTO(1L, 2L,
                1L, "2024-02-21", "2024-02-24", 2,
                ReservationRequestStatus.ACCEPTED, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-02-21");
        Date endDate = sdf.parse("2024-02-24");
        when(availabilityService.checkForDateRange(2L, startDate, endDate)).thenReturn(true);
        AccommodationViewDTO accommodation = new AccommodationViewDTO(2L, "title", "description",
                null, new ArrayList<Amenity>(), new ArrayList<Image>(), new ArrayList<AvailabilityDTO>(),
                new ArrayList<Price>(), new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                2L, 2, 3, false, 0);
        when(accommodationService.findOne(2L)).thenReturn(accommodation);
        when(accommodationService.findPriceForDateRange(2L, startDate, endDate, 2)).thenReturn(250d);
        ReservationRequestDTO returnedRequest = service.create(requestDTO);
        assertEquals(returnedRequest.getStatus(), ReservationRequestStatus.ACCEPTED);
        assertEquals(returnedRequest, approvedRequest);
        verify(availabilityService).checkForDateRange(2L, startDate, endDate);
        verify(accommodationService, times(2)).findOne(2L);
        verify(accommodationService).findPriceForDateRange(2L, startDate, endDate, 2);
        verify(reservationService, times(1)).create(returnedRequest);
        verify(requestRepository, times(1)).save(any());
        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(reservationService);
        verifyNoInteractions(reservationRepository);
    }
}
