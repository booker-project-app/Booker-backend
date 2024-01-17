package booker.BookingApp.dto.requestsAndReservations;

import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.enums.ReservationStatus;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public @Data @AllArgsConstructor @NoArgsConstructor class ReservationDTO {
    private Long guestId;
    private Long accommodationId;
    private Long id;
    private String fromDate;
    private String toDate;
    private int numberOfGuests;
    private ReservationRequestStatus requestStatus;
    private ReservationStatus status;
    private boolean deleted;
    private double price;
    private String toTime;

    public static ReservationDTO makeFromReservation(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.guestId = reservation.getGuestId();
        reservationDTO.accommodationId = reservation.getAccommodation().getId();
        reservationDTO.id = reservation.getId();
        reservationDTO.fromDate = reservation.getFromDate();
        reservationDTO.toDate = reservation.getToDate();
        reservationDTO.numberOfGuests = reservation.getNumberOfGuests();
        reservationDTO.requestStatus = reservation.getRequestStatus();
        reservationDTO.status = reservation.getStatus();
        reservationDTO.deleted = reservation.isDeleted();
        reservationDTO.price = reservation.getPrice();
        reservationDTO.toTime = reservationDTO.getToTime();
        return  reservationDTO;
    }
}
