package springBoot.study.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springBoot.study.blog.dto.CommentDto;
import springBoot.study.blog.exception.BlogAPIException;
import springBoot.study.blog.exception.ResourceNotFoundException;
import springBoot.study.blog.models.Comment;
import springBoot.study.blog.models.Post;
import springBoot.study.blog.repository.CommentRepository;
import springBoot.study.blog.repository.PostRepository;
import springBoot.study.blog.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository ;
    private PostRepository postRepository;
    private ModelMapper mapper ;

    public CommentServiceImpl(CommentRepository commentRepository , PostRepository postRepository , ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper ;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto) ;
        // retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post" , "id" , postId));
        // set post to comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment) ;
        return mapToDto(comment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
    List<Comment> comments = commentRepository.findByPostId(postId);
    return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post" , "id" , postId)) ;
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment" , "id" ,commentId)) ;
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "comment does not belong to post") ;
        }
        return mapToDto(comment) ;

    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post" , "id" , postId)) ;
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment" , "id" ,commentId)) ;
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment) ;
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post" , "id" , postId)) ;
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment" , "id" ,commentId)) ;
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
    }
        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment){
       CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto ;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto , Comment.class);
        return comment ;
    }
}
