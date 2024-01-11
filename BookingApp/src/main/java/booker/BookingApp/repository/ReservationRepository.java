package booker.BookingApp.repository;

import booker.BookingApp.model.requestsAndReservations.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // TODO change accommodation_id

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.guestId = ?1 " +
            "AND r.accommodation.owner_id = ?2 " +
            "AND r.status != 'CANCELED'" +
            "AND PARSEDATETIME(FORMATDATETIME(r.toDate, 'yyyy-MM-dd'), 'yyyy-MM-dd') < CURRENT_DATE")

    public List<Reservation> findAllForGuest(Long guestId, Long ownerId);

    @Query(value="select r from Reservation r where r.accommodation.id=:accommodationId")
    public List<Reservation> findAllForAccommodation(@Param("accommodationId") Long accommodationId);

    @Query(value = "SELECT DISTINCT r.guestId FROM Reservation r WHERE r.accommodation.owner_id=:ownerId AND r.status='ACCEPTED' " +
            "AND PARSEDATETIME(FORMATDATETIME(r.toDate, 'yyyy-MM-dd'), 'yyyy-MM-dd') < CURRENT_DATE")
    public List<Long> getAllGuestIdsForOwner(@Param("ownerId") Long ownerId);
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.guestId = ?1 " +
            "AND r.accommodation.id = ?2 " +
            "AND PARSEDATETIME(r.toDate, 'yyyy-MM-dd HH:mm:ss') < CURRENT_TIMESTAMP")
    public List<Reservation> findAllForGuestInAccommodation(Long guestId, Long accommodationId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.accommodation.owner_id = ?1 " +
            "AND r.guestId = ?2 " +
            "AND PARSEDATETIME(FORMATDATETIME(r.toDate, 'yyyy-MM-dd'), 'yyyy-MM-dd') < CURRENT_DATE")
    public List<Reservation> findAllPastForOwner(Long ownerId, Long guestId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.guestId = ?1 " +
            "AND r.accommodation.owner_id = ?2 " +
            "AND PARSEDATETIME(FORMATDATETIME(r.toDate, 'yyyy-MM-dd'), 'yyyy-MM-dd') < CURRENT_DATE")
    public List<Reservation> findAllPastForGuest(Long guestId, Long ownerId);
}
