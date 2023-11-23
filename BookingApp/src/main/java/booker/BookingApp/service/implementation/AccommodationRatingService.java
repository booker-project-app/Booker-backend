/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.accommodation.AccommodationRating;
import booker.BookingApp.repository.AccommodationRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationRatingService {
    @Autowired
    private AccommodationRatingRepository accommodationRatingRepository;

    public List<AccommodationRating> findAll() {
        return accommodationRatingRepository.findAll();
    }

    public List<AccommodationRating> findAllForAccommodation(Long id) {
        List<AccommodationRating> ratings = accommodationRatingRepository.findAll();
        List<AccommodationRating> accommodationRatings = new ArrayList<>();

        for(AccommodationRating accommodationRating : ratings) {
            if (accommodationRating.getAccommodationId() == id) {
                accommodationRatings.add(accommodationRating);
            }
        }
        return accommodationRatings;
    }

    public List<AccommodationRating> findAllReported() {
        List<AccommodationRating> ratings = accommodationRatingRepository.findAll();
        List<AccommodationRating> reported = new ArrayList<>();

        for (AccommodationRating accommodationRating : ratings) {
            if (accommodationRating.isReported()) {
                reported.add(accommodationRating);
            }
        }
        return reported;
    }

    public AccommodationRating findOne(Long id) {
        if (accommodationRatingRepository.findOne(id) == null) {
            return null;
        }
        return accommodationRatingRepository.findOne(id);
    }

    public AccommodationRating create(AccommodationRating accommodationRating) {
        return accommodationRatingRepository.create(accommodationRating);
    }

    public AccommodationRating update(AccommodationRating accommodationRating) {
        return accommodationRatingRepository.update(accommodationRating);
    }

    public void remove(Long id) {
        accommodationRatingRepository.deleteById(id);
    }

    public AccommodationRating save(AccommodationRating accommodationRating) {
        return accommodationRatingRepository.save(accommodationRating);
    }
}*/
