package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.users.CreateReportUserDTO;
import booker.BookingApp.dto.users.UserReportDTO;
import booker.BookingApp.model.users.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User findOne(Long id);
    List<User> findAll();
    User save(User user) throws InterruptedException;
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User activateProfile(String activationLink);

    void uploadImage(Long userId, MultipartFile image) throws IOException;

    String getImage(Long id) throws IOException;

    void saveProfilePicture(String imageBase64String, Long id) throws IOException;
}
