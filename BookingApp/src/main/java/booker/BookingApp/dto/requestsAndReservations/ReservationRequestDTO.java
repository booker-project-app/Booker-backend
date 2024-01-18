package booker.BookingApp.dto.requestsAndReservations;

import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.requestsAndReservations.ReservationRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public @Data @AllArgsConstructor class ReservationRequestDTO {
    @NotNull
    private Long guestId;
    @NotNull
    private Long accommodationId;
    private Long id;
    @NotEmpty
    private String fromDate;
    @NotEmpty
    private String toDate;
    @Min(1)
    private int numberOfGuests;
    @NotNull
    private ReservationRequestStatus status;
    private boolean deleted;
    @Min(0)
    private double price;

    public static ReservationRequestDTO makeFromRequest(ReservationRequest request){
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(
                request.getGuestId(),
                request.getAccommodationId(),
                request.getId(),
                request.getFromDate(),
                request.getToDate(),
                request.getNumberOfGuests(),
                request.getStatus(),
                request.isDeleted(),
                request.getPrice()
        );
        return  requestDTO;
    }

    public static ReservationRequest makeRequestFromDTO(ReservationRequestDTO requestDTO){
        ReservationRequest request = new ReservationRequest(
                requestDTO.getId(),
                requestDTO.getGuestId(),
                requestDTO.getAccommodationId(),
                requestDTO.getFromDate(),
                requestDTO.getToDate(),
                requestDTO.getNumberOfGuests(),
                requestDTO.getStatus(),
                requestDTO.isDeleted(),
                requestDTO.getPrice()
        );
        return  request;
    }
}
