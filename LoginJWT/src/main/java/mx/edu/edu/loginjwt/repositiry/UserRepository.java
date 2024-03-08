package mx.edu.edu.loginjwt.repositiry;

import mx.edu.edu.loginjwt.entity.Role;
import mx.edu.edu.loginjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
