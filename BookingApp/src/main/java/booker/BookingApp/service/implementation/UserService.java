/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.User;
import booker.BookingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findOne(Long id) {
        if (userRepository.findOneById(id) == null) {
            return null;
        }
        return userRepository.findOneById(id);
    }

    public User findOneByEmail(String email) {
        if (userRepository.findOneByEmail(email) == null) {
            return null;
        }
        return userRepository.findOneByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}*/
