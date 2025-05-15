package springBoot.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springBoot.study.blog.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(long categoryId) ;
    @Query("SELECT p FROM Post p WHERE " + "lower(p.title) LIKE CONCAT('%', lower(:query), '%')"
            + " OR lower(p.description) LIKE CONCAT('%', lower(:query), '%')"
            + " OR lower(p.content) LIKE CONCAT('%', lower(:query), '%')")

    List<Post> searchPosts(String query) ;

}
