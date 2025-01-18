package springBoot.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springBoot.study.blog.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
