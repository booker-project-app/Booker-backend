package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.AccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.model.accommodation.AccommodationComment;

import java.util.List;

public interface IAccommodationCommentService {
    AccommodationComment findOne(Long id);
    List<AccommodationComment> findAll();
    List<AccommodationComment> findAllForAccommodation(Long accommodationId);
    void remove(Long id);
    AccommodationCommentDTO create(CreateAccommodationCommentDTO accommodationCommentDTO);
    AccommodationCommentDTO update(AccommodationCommentDTO accommodationCommentDTO);
    void delete(Long id);
    void report(Long id);
    AccommodationComment save(AccommodationComment accommodationComment);
    List<AccommodationComment> findAllReported();
    List<AccommodationCommentDTO> findAllNotDeletedForAccommodation(Long accommodationId);
    void deleteForAdmin(Long id);
    void approveComment(Long id);
}
