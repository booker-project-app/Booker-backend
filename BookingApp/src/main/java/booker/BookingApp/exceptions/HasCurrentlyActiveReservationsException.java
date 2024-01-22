package booker.BookingApp.exceptions;

public class HasCurrentlyActiveReservationsException extends RuntimeException{
    public HasCurrentlyActiveReservationsException(String message) {super(message);}
}
