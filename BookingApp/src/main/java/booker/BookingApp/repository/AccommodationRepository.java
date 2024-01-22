package booker.BookingApp.repository;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.AccommodationRating;
import booker.BookingApp.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AccommodationRepository  extends JpaRepository<Accommodation, Long> {
    @Query(value="select a from Accommodation a WHERE a.address.city like %:location% and :people >= a.min_capacity and :people <= a.max_capacity and a.accepted = true")
    List<Accommodation> searchAccommodations(@Param("location") String location, @Param("people") int people);

    @Query(value = "SELECT a FROM Accommodation a WHERE a.owner_id = :ownerId")
    List<Accommodation>findAllForOwner(@Param("ownerId") Long ownerId);

    @Query(value = "SELECT a FROM Accommodation a WHERE a.owner_id = :ownerId and a.accepted = :accepted")
    List<Accommodation>findSpecifiedForOwner(@Param("ownerId") Long ownerId, @Param("accepted") Boolean accepted);

    @Query(value = "DELETE FROM Accommodation WHERE owner_id = :ownerId")
    void deleteForOwner(@Param("ownerId") Long ownerId);

    @Query(value = "SELECT a FROM Accommodation a WHERE a.accepted = :accepted")
    List<Accommodation>findUnapproved(@Param("accepted") Boolean accepted);

    @Query(value = "SELECT a.id FROM Accommodation a WHERE a.title = :accName")
    Long findIdByName(@Param("accName") String accName);

    @Query(value = "SELECT a.id FROM Accommodation a")
    ArrayList<Long> findAllIds();
}
