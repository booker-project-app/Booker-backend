package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.users.CreateReportUserDTO;
import booker.BookingApp.dto.users.UserReportDTO;
import booker.BookingApp.model.users.UserReport;

import java.util.List;

public interface IUserReportService {
    public UserReportDTO create(CreateReportUserDTO createReportUserDTO);
    public List<UserReportDTO> findAll();

    List<UserReport> getAllForUser(Long userId);

    void blockOrUnblock(Long userId, boolean blocked);
}
