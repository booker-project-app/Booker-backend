package booker.BookingApp.repository;

import booker.BookingApp.model.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.blocked = true")
    public List<User> findAllBlocked();

}
