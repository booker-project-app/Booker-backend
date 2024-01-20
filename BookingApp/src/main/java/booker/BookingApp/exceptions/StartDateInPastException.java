package booker.BookingApp.exceptions;

public class StartDateInPastException extends RuntimeException{
    public StartDateInPastException(String message) {super(message);}
}
