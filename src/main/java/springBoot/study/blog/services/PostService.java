package springBoot.study.blog.services;

import springBoot.study.blog.dto.PostDto;
import springBoot.study.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost (PostDto postDto);
    PostResponse getAllPosts(int pageNo , int pageSize, String sortBy ,String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id) ;
    void deletePostById(long id );
}
