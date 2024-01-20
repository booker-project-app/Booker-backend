package booker.BookingApp.exceptions;

public class BothDatesInPastException extends RuntimeException{
    public BothDatesInPastException(String message) {super(message);}
}
