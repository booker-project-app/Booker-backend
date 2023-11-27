package booker.BookingApp.service.implementation;

import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

   @Override
    public User findOne(Long id){
        return userRepository.findById(id).orElseGet(null);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
    public List<User> findAllBlocked() {
       return userRepository.findAllBlocked();
    }

}