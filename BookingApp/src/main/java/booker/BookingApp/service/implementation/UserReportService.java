package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.users.CreateReportUserDTO;
import booker.BookingApp.dto.users.UserReportDTO;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.User;
import booker.BookingApp.model.users.UserReport;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserReportRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IUserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserReportService implements IUserReportService {
    @Autowired
    private UserReportRepository userReportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Override
    public UserReportDTO create(CreateReportUserDTO createReportUserDTO) {
        UserReport userReport = new UserReport();
        userReport.setReason(createReportUserDTO.getReason());
        userReport.setDate(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                if (user.getRole() == Role.GUEST) {
                    System.out.println("Owner ID: "+ user.getId());
                    System.out.println("Guest ID: " + createReportUserDTO.getReportedId());
                    if (reservationRepository.findAllPastForGuest(user.getId(), createReportUserDTO.getReportedId()).size() == 0){
                        System.out.println("Owner ID: "+ user.getId());
                        System.out.println("Guest ID: " + createReportUserDTO.getReportedId());
                        throw new RuntimeException("You didn't have any reservations in this owner's accommodations before!");
                    }
                }

                if (user.getRole() == Role.OWNER) {
                    System.out.println("Owner ID: "+ user.getId());
                    System.out.println("Guest ID: " + createReportUserDTO.getReportedId());
                    if (reservationRepository.findAllPastForGuest(createReportUserDTO.getReportedId(), user.getId()).size() == 0) {
                        System.out.println("Owner ID: "+ user.getId());
                        System.out.println("Guest ID: " + createReportUserDTO.getReportedId());
                        throw new RuntimeException("This guest didn't have any reservation in your accommodations before!");
                    }
                }


                userReport.setReporter(user);
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }
        User reportedUser = userRepository.findById(createReportUserDTO.getReportedId()).orElseGet(null);
        userReport.setReported(reportedUser);
        if (reportedUser.getRole() == Role.GUEST) {
            Guest guest = (Guest) reportedUser;
            guest.setReported(true);
            userRepository.save(guest);
        } else if (reportedUser.getRole() == Role.OWNER) {
            Owner owner = (Owner) reportedUser;
            owner.setReported(true);
            userRepository.save(owner);
        }
        userReportRepository.save(userReport);
        UserReportDTO userReportDTO = UserReportDTO.createFromUserReport(userReport);
        return userReportDTO;
    }

    @Override
    public List<UserReportDTO> findAll() {
        List<UserReport> userReports = userReportRepository.findAll();
        List<UserReportDTO> userReportDTOS = new ArrayList<>();
        for (UserReport userReport : userReports) {
            UserReportDTO userReportDTO = UserReportDTO.createFromUserReport(userReport);
            userReportDTOS.add(userReportDTO);
        }

        return userReportDTOS;
    }
}
