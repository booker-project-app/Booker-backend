package booker.BookingApp.repository;

import booker.BookingApp.model.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
