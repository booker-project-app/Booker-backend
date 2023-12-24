package booker.BookingApp.service.interfaces;

import booker.BookingApp.model.users.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    public User findOne(Long id);
    public List<User> findAll();
    public User save(User user) throws InterruptedException;
    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
    public User activateProfile(String activationLink);
    void uploadImage(Long userId, MultipartFile image) throws IOException;
}
