package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.model.accommodation.Availability;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public interface IAvailabilityService {
    Availability create(Long accommodationId, AvailabilityDTO availabilityDTO);

    AvailabilityDTO update(Long accommodationId, AvailabilityDTO availabilityDTO);

    ArrayList<AvailabilityDTO> findAllFreeInRange(Long accommodationId, String startDate, String endDate) throws IOException;

    boolean checkForDate(Long accommodationId, String date) throws ParseException;

    boolean checkForDateRange(Long accommodationId, Date startDate, Date endDate);

    boolean checkIfAvailable(Long accommodationId, Date startDate, Date endDate);

    void markAsNotAvailable(Long accommodationId, String startDate, String endDate);

    void refactorAvailability(Availability availability, Date fromDate, Date toDate);

    boolean checkForOverlaps(ReservationRequestDTO requestDTO, ReservationRequestDTO acceptedRequest);

    void returnAvailabilities(Long accommodationId, Date startDate, Date endDate);
}
