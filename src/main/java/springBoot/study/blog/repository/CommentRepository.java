package springBoot.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springBoot.study.blog.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId) ;
}
