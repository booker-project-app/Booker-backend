package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ManualReservationRequestApprovalTest {

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
    public void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return false, accommodation is not available")
    public void TestAccommodationNotAvailable_returnsFalse() throws IOException, ParseException {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-03-05");
        Date endDate = sdf.parse("2024-03-15");
        when(availabilityService.checkForDateRange(1L, startDate, endDate)).thenReturn(false);
        assertFalse(service.acceptOrDecline(true, requestDTO));
        verify(availabilityService).checkForDateRange(1L, startDate, endDate);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(reservationService);
        verifyNoInteractions(requestRepository);
        verifyNoInteractions(reservationRepository);
    }

    @Test
    @DisplayName("Should return true, update request status and create reservation")
    public void TestCreateReservationManuallyAndDenyOverlapRequests_success() throws IOException, ParseException {
        ReservationRequestDTO accRequest1 = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-21", "2024-03-24", 2,
                ReservationRequestStatus.WAITING, false, 250);
        ReservationRequestDTO accRequest2 = new ReservationRequestDTO(1L, 1L,
                2L, "2024-02-17", "2024-02-22", 2,
                ReservationRequestStatus.WAITING, false, 250);
        ReservationRequestDTO accRequest3 = new ReservationRequestDTO(1L, 1L,
                3L, "2024-02-22", "2024-02-23", 2,
                ReservationRequestStatus.WAITING, false, 250);
        ReservationRequestDTO accRequest4 = new ReservationRequestDTO(1L, 1L,
                4L, "2024-03-26", "2024-03-28", 2,
                ReservationRequestStatus.WAITING, false, 250);
        ArrayList<ReservationRequestDTO> accRequests = new ArrayList<>();
        accRequests.add(accRequest1); accRequests.add(accRequest2); accRequests.add(accRequest3); accRequests.add(accRequest4);

        ReservationRequestDTO newRequest = new ReservationRequestDTO(1L, 1L,
                5L, "2024-02-21", "2024-02-24", 2,
                ReservationRequestStatus.WAITING, false, 250);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-02-21");
        Date endDate = sdf.parse("2024-02-24");
        when(availabilityService.checkForDateRange(1L, startDate, endDate)).thenReturn(true);
        when(accommodationService.findAccommodationRequests(1L)).thenReturn(accRequests);
        when(availabilityService.checkForOverlaps(accRequest1, newRequest)).thenReturn(false);
        when(availabilityService.checkForOverlaps(accRequest2, newRequest)).thenReturn(true);
        when(availabilityService.checkForOverlaps(accRequest3, newRequest)).thenReturn(true);
        when(availabilityService.checkForOverlaps(accRequest4, newRequest)).thenReturn(false);
        assertTrue(service.acceptOrDecline(true, newRequest));
        verify(availabilityService).checkForDateRange(1L, startDate, endDate);
        verify(accommodationService).findAccommodationRequests(1L);
        verify(availabilityService, times(1)).checkForOverlaps(accRequest1, newRequest);
        verify(availabilityService, times(1)).checkForOverlaps(accRequest2, newRequest);
        verify(availabilityService, times(1)).checkForOverlaps(accRequest3, newRequest);
        verify(availabilityService, times(1)).checkForOverlaps(accRequest4, newRequest);
        verify(reservationService, times(1)).create(newRequest);
        verify(requestRepository, times(3)).save(any());
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(requestRepository);
        verifyNoInteractions(reservationRepository);
    }
}
