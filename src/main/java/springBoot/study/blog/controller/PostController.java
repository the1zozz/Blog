package springBoot.study.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.history.Revision;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springBoot.study.blog.dto.PostDtoRequest;

import springBoot.study.blog.dto.PostDtoResponse;
import springBoot.study.blog.dto.PostResponse;
import springBoot.study.blog.models.Post;
import springBoot.study.blog.repository.PostRevisionRepository;
import springBoot.study.blog.services.PostService;
import springBoot.study.blog.util.AppConstants;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resources "
)
public class PostController {

    private final PostService postService;
    private final PostRevisionRepository postRevisionRepository;

    public PostController(PostService postService, PostRevisionRepository postRevisionRepository) {
        this.postService = postService;
        this.postRevisionRepository = postRevisionRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create post REST API",
            description = "Create post is uses to save a new post to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    public ResponseEntity<PostDtoResponse> createPost(@Valid @RequestBody PostDtoRequest postDtoRequest) {
        return new ResponseEntity<>(postService.createPost(postDtoRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get all posts REST API",
            description = "Get All Posts is used to get all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get post By Id is used to Get Post By Id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    public ResponseEntity<PostDtoResponse> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update post REST API",
            description = "Update post is uses to update post By Id to the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    public ResponseEntity<PostDtoResponse> updatePost(@Valid @RequestBody PostDtoRequest postDtoRequest, @PathVariable long id) {
        PostDtoResponse postResponse = postService.updatePost(postDtoRequest, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete post REST API",
            description = "Delete post By Id is uses to delete post By Id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("post entity has been deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get post By Category REST API",
            description = "Get post By Category Id is uses to get post By Category Id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    public ResponseEntity<List<PostDtoResponse>> getPostsByCategory(@PathVariable long categoryId) {
        List<PostDtoResponse> postDtoResponses = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtoResponses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDtoResponse>> searchPosts(@RequestParam(value = "query") String query) {
        List<PostDtoResponse> postDtoResponses = postService.searchPosts(query);
        return ResponseEntity.ok(postDtoResponses);
    }

    @GetMapping("/{id}/revisions")
    public List<Revision<Integer, Post>> getRevisions(@PathVariable Long id) {
        return postRevisionRepository.findRevisions(id).getContent();
    }
}