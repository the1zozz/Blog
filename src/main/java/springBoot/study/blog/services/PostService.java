package springBoot.study.blog.services;

import springBoot.study.blog.dto.PostDtoRequest;
import springBoot.study.blog.dto.PostDtoResponse;
import springBoot.study.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDtoResponse createPost (PostDtoRequest postDtoRequest);
    PostResponse getAllPosts(int pageNo , int pageSize, String sortBy ,String sortDir);
    PostDtoResponse getPostById(long id);
    PostDtoResponse updatePost(PostDtoRequest postDtoRequest, long id) ;
    void deletePostById(long id );
    List<PostDtoResponse> getPostsByCategory(long categoryId);
    List<PostDtoResponse> searchPosts(String query);
}
