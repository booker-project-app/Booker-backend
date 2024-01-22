package booker.BookingApp.repository;

import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.model.users.Admin;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByActivationLink(String activationLink);

    @Query(value = "SELECT g FROM Guest g WHERE g.role = 'GUEST'")
    List<Guest> getAllGuests();

    @Query(value = "SELECT o FROM Owner o WHERE o.role = 'OWNER'")
    List<Owner> getAllOwners();

    @Query(value = "SELECT u FROM User u WHERE u.id = :id")
    User getOne(@Param("id") Long id);

    @Query(value = "SELECT a FROM Admin a WHERE a.role = 'ADMIN'")
    Admin getAdmin();

    User save(User user);

    @Query(value = "UPDATE User u SET u.deleted = true WHERE u.id = :id")
    User delete(@Param("id") Long id);

    @Query(value = "SELECT count(r) from ReservationRequest r WHERE r.guestId = :guestId AND r.deleted=true")
    int getNumOfCancellations(@Param("guestId") Long guestId);

    @Query(value = "SELECT u FROM User u WHERE u.reported=true")
    List<User> getAllReported();
}
