package booker.BookingApp.repository;

import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Long> {
    @Query("select oc from OwnerComment oc where oc.owner.id = ?1 and oc.deleted=false")
    public List<OwnerComment> findAllForOwner(Long ownerId);

    @Query("select oc from OwnerComment oc where oc.reported = true")
    public List<OwnerComment> findAllReported();

    @Query("select oc from OwnerComment  oc where oc.owner.id = ?1 and oc.deleted = false and oc.approved = true")
    public List<OwnerComment> findAllNotDeletedForOwner(Long ownerId);

}