package springBoot.study.blog.repository;

import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import springBoot.study.blog.models.Post;
@Repository
public interface PostRevisionRepository extends RevisionRepository<Post, Long, Integer> {
}
