package booker.BookingApp.repository;

import booker.BookingApp.model.requestsAndReservations.ReservationRequest;
import booker.BookingApp.model.users.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
}
