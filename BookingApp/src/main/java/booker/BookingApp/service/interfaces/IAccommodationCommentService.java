package booker.BookingApp.service.interfaces;

import booker.BookingApp.model.accommodation.AccommodationComment;

import java.util.List;

public interface IAccommodationCommentService {
    public AccommodationComment findOne(Long id);
    public List<AccommodationComment> findAll();
    public List<AccommodationComment> findAllForAccommodation(Long accommodationId);
    public void remove(Long id);
    public AccommodationComment save(AccommodationComment accommodationComment);
    public List<AccommodationComment> findAllReported();
}
