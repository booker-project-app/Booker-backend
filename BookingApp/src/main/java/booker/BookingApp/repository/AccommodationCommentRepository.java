package booker.BookingApp.repository;

import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import booker.BookingApp.model.accommodation.AccommodationComment;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationCommentRepository extends JpaRepository<AccommodationComment, Long> {
    @Query("select ac from AccommodationComment ac where ac.accommodation.id = ?1 and ac.deleted=false")
    List<AccommodationComment> findAllForAccommodation(Long accommodationId);

    @Query("select ac from AccommodationComment ac where ac.reported = true")
    List<AccommodationComment> findAllReported();

    @Query("select ac from AccommodationComment ac where ac.accommodation.id = ?1 and ac.deleted = false and ac.approved = true")
    List<AccommodationComment> findAllNotDeletedForAccommodation(Long accommodationId);
}
