package booker.BookingApp.repository;

import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRatingRepository extends JpaRepository<OwnerRating, Long> {

    @Query("select o from OwnerRating o where o.reported = true")
    List<OwnerRating> findAllReported();
    @Query("select o from OwnerRating o where o.owner.id = ?1 and o.deleted=false")
    List<OwnerRating> findAllForOwner(Long ownerId);
}
