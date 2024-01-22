package booker.BookingApp.repository;

import booker.BookingApp.model.accommodation.AccommodationRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationRatingRepository extends JpaRepository<AccommodationRating, Long> {

    @Query("select ar from AccommodationRating ar where ar.accommodation.id = ?1 and ar.deleted=false")
    List<AccommodationRating> findAllForAccommodation(Long accommodationId);

    @Query("select ar from AccommodationRating ar where ar.reported = true")
    List<AccommodationRating> findAllReported();
}
