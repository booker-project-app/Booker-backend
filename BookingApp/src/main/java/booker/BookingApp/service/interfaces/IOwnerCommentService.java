package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerCommentDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;

import java.util.List;

public interface IOwnerCommentService {
    public OwnerComment findOne(Long id);
    public List<OwnerComment> findAll();
    public List<OwnerCommentDTO> findAllForOwner(Long ownerId);
    public void remove(Long id);
    public OwnerCommentDTO create(CreateOwnerCommentDTO ownerCommentDTO);
    public OwnerCommentDTO update(OwnerCommentDTO ownerCommentDTO);
    public void delete(Long id);
    public List<OwnerComment> findAllReported();
    public void report(Long id);
    public List<OwnerCommentDTO> findAllNotDeletedForOwner(Long ownerId);
    public void deleteForAdmin(Long id);
}
