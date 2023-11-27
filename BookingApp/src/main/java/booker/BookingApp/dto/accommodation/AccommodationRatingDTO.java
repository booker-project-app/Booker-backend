package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.AccommodationRating;
import lombok.Data;

import java.util.Date;

public @Data class AccommodationRatingDTO {
    private Long id;
    private Long accommodationId;
    private Long guestId;
    private float rate;
    private Date date;
    private boolean reported;


    public AccommodationRatingDTO(AccommodationRating accommodationRating) {
        this(accommodationRating.getId(), accommodationRating.getAccommodation().getId(), accommodationRating.getGuest().getId(), accommodationRating.getRate(), accommodationRating.getDate(), accommodationRating.isReported());
    }

    public AccommodationRatingDTO(Long id, Long accommodationId, Long guestId, float rate, Date date, boolean reported) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.guestId = guestId;
        this.rate = rate;
        this.date = date;
        this.reported = reported;
    }
}
