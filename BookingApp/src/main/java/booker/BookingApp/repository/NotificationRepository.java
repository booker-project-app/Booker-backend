package booker.BookingApp.repository;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT n FROM Notification n WHERE n.userId = :userId")
    List<Notification> findAllPersonal(@Param("userId") Long userId);
}
