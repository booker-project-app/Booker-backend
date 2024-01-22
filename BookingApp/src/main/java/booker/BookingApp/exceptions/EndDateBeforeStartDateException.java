package booker.BookingApp.exceptions;

public class EndDateBeforeStartDateException extends RuntimeException{
    public EndDateBeforeStartDateException(String message) {super(message);}
}
