package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.ReservationRequestRepository;
import booker.BookingApp.service.implementation.ReservationRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DenyReservationRequestTest {

    @Mock
    ReservationRequestRepository requestRepository;

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationRequestService service;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return true, deny reservation request")
    public void TestDenyReservationRequest_success() {
        ReservationRequestDTO newRequest = new ReservationRequestDTO(1L, 1L,
                5L, "2024-02-21", "2024-02-24", 2,
                ReservationRequestStatus.WAITING, false, 250);
        assertTrue(service.acceptOrDecline(false, newRequest));
        verify(requestRepository, times(1)).save(any());
        verifyNoMoreInteractions(requestRepository);
        verifyNoInteractions(reservationRepository);
    }
}
