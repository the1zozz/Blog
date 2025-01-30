package springBoot.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springBoot.study.blog.models.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email) ;
    Optional<User> findByUsernameOrEmail(String username , String email) ;
    Optional<User> findByUsername(String username) ;
    Boolean existsByUsername(String username) ;
    Boolean existsByEmail(String email);

}
