package si.fri.skupina06.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.fri.skupina06.user.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
}