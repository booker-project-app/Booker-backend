package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.users.UserDTO;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Image;
import booker.BookingApp.model.users.ProfilePicture;
import booker.BookingApp.model.users.User;
//import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.repository.ProfilePictureRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IUserService;
import booker.BookingApp.util.ImageUploadUtil;
import booker.BookingApp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService implements IUserService {

    @Value("../../Booker-frontend/booker/src/assets/images")
    private String imagesDirPathFront;

    @Value("../../../../../res/drawable")
    private String imagesDirPathMobile;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Override
    public User findOne(Long id){
        return userRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }


    @Override
    public User save(User user) throws InterruptedException {
        user.setActivationLink(StringUtil.generateRandomStr(10));
        System.out.println(user.getActivationLink());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        user.setActivationTimestamp(new Date());
        user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        emailService.sendNotificaitionAsync(user);
        userRepository.save(user);

        return user;
    }
    @Override
    public User activateProfile(String activationLink) {
        User user =  userRepository.findByActivationLink(activationLink);
        if (user == null) {
            return null;
        }
        if(user.isActivationExpired()) {
            return null;
        }

        if (isActivationLinkExpired(user.getActivationTimestamp())) {
            user.setActivationExpired(true);
            System.out.println("Activation link expired!");
            return null;
        }


        user.setActivated(true);
        user.setActivationTimestamp(new Date());
        userRepository.save(user);
        return user;
    }

    @Override
    public void uploadImage(Long userId, MultipartFile image) throws IOException {
        User user = findOne(userId);
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        // save to frontend folder
        ImageUploadUtil.saveImage(imagesDirPathFront, fileName, image);
        // save to mobile app folder
        ImageUploadUtil.saveImage(imagesDirPathMobile, fileName, image);
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setPath("../../assets/images/" + fileName);
        profilePicture.setPath_mobile("../../../../../res/drawable/" + fileName);
        Optional<User> u = userRepository.findById(userId);
        profilePicture.setUser(u.get());
        profilePictureRepository.save(profilePicture);
    }


    private boolean isActivationLinkExpired(Date activationTimestamp) {
        //long expirationTimeMillis = 24 * 60 * 60 * 1000;
        long expirationTimeMillis = 5 * 60 * 1000;
        long currentTimeMillis = System.currentTimeMillis();
        return (currentTimeMillis - activationTimestamp.getTime()) > expirationTimeMillis;
    }
    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null) {
            return null;
        }

        return user;
    }







}