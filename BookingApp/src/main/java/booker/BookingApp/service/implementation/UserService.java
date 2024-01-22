package booker.BookingApp.service.implementation;

import booker.BookingApp.model.users.ProfilePicture;
import booker.BookingApp.model.users.User;
//import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.repository.ProfilePictureRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IUserService;
import booker.BookingApp.util.ImageUploadUtil;
import booker.BookingApp.util.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService implements IUserService {

    @Value("src/main/resources/images/user")
    private String imagesDirPath;

    @Value("../../Booker-frontend/booker/src/assets/images/user")
    private String imagesDirPathFront;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public User findOne(Long id){
        return userRepository.findById(id).orElse(null);
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


    @Override
    public void uploadImage(Long userId, MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPathFront + userId);
        // clean directory before upload new image
        File directory = new File(imagesDirPathFront + userId);
        if(directory.exists() && directory.isDirectory()) {
            FileUtils.cleanDirectory(directory);
        }
        File directoryMobile = new File(imagesDirPath + userId);
        if(directoryMobile.exists() && directoryMobile.isDirectory()) {
            FileUtils.cleanDirectory(directoryMobile);
        }

        // save to frontend folder
        ImageUploadUtil.saveImage(uploadDir, fileName, image);
        // save to mobile app folder
        ImageUploadUtil.saveImage(imagesDirPath + userId, fileName, image);

        ProfilePicture profilePicture = profilePictureRepository.getProfilePictureForUser(userId);
        profilePicture.setPath("../../assets/images/user" + userId + "/" + fileName);
        profilePicture.setPath_mobile(fileName);
        profilePictureRepository.save(profilePicture);
    }

    @Override
    public String getImage(Long id) throws IOException {
        String imageBase64 = "";
        String directoryPath = StringUtils.cleanPath(imagesDirPath + id);
        File directory = new File(directoryPath);
        if(directory.exists() && directory.isDirectory()) {
            File[] images = directory.listFiles();
            if(images!=null) {
                for(File image : images) {
                    if(image.isFile()) {
                        byte[] imageData = Files.readAllBytes(image.toPath());
                        imageBase64 = Base64.getEncoder().encodeToString(imageData);
                    }
                }
            }
        }
        return imageBase64;
    }

    @Override
    public void saveProfilePicture(String imageBase64String, Long id) throws IOException {
        byte[] imageBytes = imageBase64String.getBytes();
        Path uploadPath = Paths.get(imagesDirPath + id);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            Path filePath = uploadPath.resolve("profile_picture.jpg");

            System.out.println("UploadPath je: " + uploadPath.toAbsolutePath());

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("Could not save file ", ioe);
        }
    }
}