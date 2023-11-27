package booker.BookingApp.service.implementation;

import booker.BookingApp.model.accommodation.AccommodationRating;
import booker.BookingApp.repository.AccommodationRatingRepository;
import booker.BookingApp.service.interfaces.IAccommodationRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationRatingService implements IAccommodationRatingService {
    @Autowired
    private AccommodationRatingRepository accommodationRatingRepository;

    @Override
    public List<AccommodationRating> findAll() {
        return accommodationRatingRepository.findAll();
    }

    public List<AccommodationRating> findAllForAccommodation(Long id) {
       return accommodationRatingRepository.findAllForAccommodation(id);
    }

    @Override
    public List<AccommodationRating> findAllReported() {
        return accommodationRatingRepository.findAllReported();
    }


    @Override
    public AccommodationRating findOne(Long id) {
        return accommodationRatingRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        accommodationRatingRepository.deleteById(id);
    }

    @Override
    public AccommodationRating save(AccommodationRating accommodationRating) {
        return accommodationRatingRepository.save(accommodationRating);
    }

}
