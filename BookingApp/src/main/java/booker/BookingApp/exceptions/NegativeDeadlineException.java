package booker.BookingApp.exceptions;

public class NegativeDeadlineException extends RuntimeException{
    public NegativeDeadlineException(String message) {super(message);}
}
