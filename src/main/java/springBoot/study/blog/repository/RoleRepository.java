package springBoot.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springBoot.study.blog.models.Role;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findByName(String name) ;
}
