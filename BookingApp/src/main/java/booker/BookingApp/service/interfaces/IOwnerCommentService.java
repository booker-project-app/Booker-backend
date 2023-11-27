package booker.BookingApp.service.interfaces;

import booker.BookingApp.model.commentsAndRatings.OwnerComment;

import java.util.List;

public interface IOwnerCommentService {
    public OwnerComment findOne(Long id);
    public List<OwnerComment> findAll();
    public void remove(Long id);
    public OwnerComment save(OwnerComment ownerComment);
    public List<OwnerComment> findAllForOwner(Long ownerId);
    public List<OwnerComment> findAllReported();
}
