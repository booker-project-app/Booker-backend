package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.users.CreateReportUserDTO;
import booker.BookingApp.dto.users.UserReportDTO;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.User;
//import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.model.users.UserReport;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IUserService;
import booker.BookingApp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

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







}