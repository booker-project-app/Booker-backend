package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerRatingDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerRatingDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerRating;

import java.util.List;

public interface IOwnerRatingService {
    OwnerRating findOne(Long id);
    List<OwnerRating> findAll();
    List<OwnerRating> findAllReported();
    void delete(Long id);
    //public OwnerRating save(OwnerRating ownerRating);
    OwnerRatingDTO create(CreateOwnerRatingDTO ownerRatingDTO);
    OwnerRatingDTO update(OwnerRatingDTO ownerRatingDTO);
    List<OwnerRating> getAllForOwner(Long ownerId);
    void report(Long id);
    void deleteForAdmin(Long id);
}
