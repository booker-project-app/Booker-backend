package booker.BookingApp.service.interfaces;

import booker.BookingApp.model.accommodation.AccommodationRating;

import java.util.List;

public interface IAccommodationRatingService {
    public List<AccommodationRating> findAll();
    public List<AccommodationRating> findAllReported();
    public AccommodationRating findOne(Long id);
    public void remove(Long id);
    public AccommodationRating save(AccommodationRating accommodationRating);
    public List<AccommodationRating> findAllForAccommodation(Long accommodationId);
}
