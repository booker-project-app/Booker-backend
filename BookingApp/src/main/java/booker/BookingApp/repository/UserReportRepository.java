package booker.BookingApp.repository;

import booker.BookingApp.model.users.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @Query(value = "SELECT r FROM UserReport r WHERE r.reportedId=:userId")
    List<UserReport> getAllForUser(@Param("userId") Long userId);
}
