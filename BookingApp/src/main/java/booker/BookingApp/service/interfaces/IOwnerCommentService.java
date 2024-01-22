package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerCommentDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;

import java.util.List;

public interface IOwnerCommentService {
    OwnerComment findOne(Long id);
    List<OwnerComment> findAll();
    List<OwnerCommentDTO> findAllForOwner(Long ownerId);
    void remove(Long id);
    OwnerCommentDTO create(CreateOwnerCommentDTO ownerCommentDTO);
    OwnerCommentDTO update(OwnerCommentDTO ownerCommentDTO);
    void delete(Long id);
    List<OwnerComment> findAllReported();
    void report(Long id);
    List<OwnerCommentDTO> findAllNotDeletedForOwner(Long ownerId);
    void deleteForAdmin(Long id);
    void approveComment(Long id);
}
