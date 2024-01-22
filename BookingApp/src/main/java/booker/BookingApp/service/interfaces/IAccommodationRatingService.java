package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.AccommodationRatingDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationRatingDTO;
import booker.BookingApp.model.accommodation.AccommodationRating;

import java.util.List;

public interface IAccommodationRatingService {
    AccommodationRating findOne(Long id);
    List<AccommodationRating> findAll();
    List<AccommodationRating> findAllForAccommodation(Long accommodationId);
    void remove(Long id);
    CreateAccommodationRatingDTO create(CreateAccommodationRatingDTO ratingDTO);
    AccommodationRatingDTO update(AccommodationRatingDTO ratingDTO);
    void delete(Long id);
    List<AccommodationRating> findAllReported();
    void report(Long id);
    void deleteForAdmin(Long id);
}
