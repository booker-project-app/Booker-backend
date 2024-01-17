package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.requestsAndReservations.ReservationDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.model.accommodation.Accommodation;

import java.util.ArrayList;

public interface IReservationService {
    ArrayList<ReservationDTO> findAll();

    ArrayList<ReservationDTO> getAllForGuest(Long guestId);

    ArrayList<ReservationDTO> getAllForAccommodation(Long accommodationId);

    ReservationDTO getOneById(Long id);

    void create(ReservationRequestDTO reservationRequest);

    void delete(Long id);

    boolean checkDeadlineExpired(String fromDateString, Accommodation accommodation);

    boolean cancel(Long reservationId);
}
