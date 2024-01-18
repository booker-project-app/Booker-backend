package booker.BookingApp.repository;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    @Query(value = "SELECT a from Availability AS a WHERE a.accommodation.id=:accommodationId AND :startDate >= a.startDate AND :startDate <=a.endDate AND :endDate >=a.startDate AND :endDate <=a.endDate")
    List<Availability> checkForDateRange(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(value = "SELECT a from Availability AS a WHERE a.accommodation.id=:accommodationId AND :date >= a.startDate AND :date <=a.endDate")
    Availability checkForDate(
            @Param("accommodationId") Long accommodationId,
            @Param("date") Date date
    );

    @Query(value = "SELECT a FROM Availability a WHERE a.accommodation.id=:accommodationId")
    List<Availability> findByAccommodationId(@Param("accommodationId") Long accommodationId);

}
