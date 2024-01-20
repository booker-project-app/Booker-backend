package booker.BookingApp.exceptions;

public class EndDateInPastException extends RuntimeException {
    public EndDateInPastException(String message) {super(message);}
}
