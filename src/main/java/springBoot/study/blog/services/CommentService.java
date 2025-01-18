package springBoot.study.blog.services;

import springBoot.study.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId , CommentDto commentDto) ;
    List<CommentDto> getCommentsByPostId(Long postId) ;
    CommentDto getCommentById(Long postId , Long CommentId) ;
    CommentDto updateCommentById (Long postId , Long commentId , CommentDto commentDto) ;
    void deleteCommentById(Long postId , Long commentId) ;
}
