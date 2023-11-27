package booker.BookingApp.model.requestsAndReservations;

import lombok.Data;

import java.util.Date;

public @Data class Reservation {
    private Date fromDate;
    private Date toDate;
    private int numberOfGuests;
    private boolean deleted;
}