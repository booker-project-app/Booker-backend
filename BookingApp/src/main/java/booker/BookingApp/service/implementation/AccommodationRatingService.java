package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationRatingDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationRatingDTO;
import booker.BookingApp.dto.accommodation.ReportAccommodationRatingDTO;
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


    @Override
    public List<AccommodationRating> findAllForAccommodation(Long id) {
        return accommodationRatingRepository.findAllForAccommodation(id);

    }

    @Override
    public List<AccommodationRating> findAllReported() {
        List<AccommodationRating> ratings = accommodationRatingRepository.findAllReported();
        return ratings;
    }

    @Override
    public void report(Long id) {
        AccommodationRating rating = accommodationRatingRepository.findById(id).orElseGet(null);
        rating.setReported(true);
        accommodationRatingRepository.save(rating);
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
    public CreateAccommodationRatingDTO create(CreateAccommodationRatingDTO ratingDTO) {
        return ratingDTO;
    }

    @Override
    public AccommodationRatingDTO update(AccommodationRatingDTO ratingDTO) {
        return ratingDTO;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteForAdmin(Long id) {
        AccommodationRating rating = accommodationRatingRepository.findById(id).orElseGet(null);
        rating.setDeleted(true);
        accommodationRatingRepository.save(rating);
    }

//    public AccommodationRating save(AccommodationRating accommodationRating) {
//        return accommodationRatingRepository.save(accommodationRating);
//    }

    //public ReportAccommodationRatingDTO report(ReportAccommodationRatingDTO reportAccommodationRatingDTO) {
        //return reportAccommodationRatingDTO;
    //}
}
