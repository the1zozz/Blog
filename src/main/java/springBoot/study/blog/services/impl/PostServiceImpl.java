package springBoot.study.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springBoot.study.blog.dto.PostDtoRequest;
import springBoot.study.blog.dto.PostDtoResponse;
import springBoot.study.blog.dto.PostResponse;
import springBoot.study.blog.exception.ResourceNotFoundException;
import springBoot.study.blog.models.Category;
import springBoot.study.blog.models.Post;
import springBoot.study.blog.repository.CategoryRepository;
import springBoot.study.blog.repository.PostRepository;
import springBoot.study.blog.services.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   private final PostRepository postRepository;
   private final ModelMapper modelMapper ;
   private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper , CategoryRepository categoryRepository ) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper ;
        this.categoryRepository = categoryRepository ;
    }

    @Override
    public PostDtoResponse createPost(PostDtoRequest postDtoRequest) {
        Category category = categoryRepository.findById(postDtoRequest.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category" , "id" , postDtoRequest.getCategoryId())) ;
        Post post = mapToEntity(postDtoRequest) ;
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        return mapToPostDtoResponse(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending() ;
        Pageable pageable = PageRequest.of(pageNo , pageSize , sort ) ;

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDtoResponse> content = listOfPosts.stream().map(this::mapToPostDtoResponse).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse() ;
        postResponse.setContent(content);
        postResponse.setPagaeNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse ;
    }

    @Override
    public PostDtoResponse getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        return mapToPostDtoResponse(post) ;
    }

    @Override
    public PostDtoResponse updatePost(PostDtoRequest postDtoRequest, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        Category category = categoryRepository.findById(postDtoRequest.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("Category" ,"id", postDtoRequest.getCategoryId())
        ) ;
        post.setCategory(category);
        post.setTitle(postDtoRequest.getTitle());
        post.setContent(postDtoRequest.getContent());
        post.setDescription(postDtoRequest.getDescription());
        Post updatedPost = postRepository.save(post) ;
        return mapToPostDtoResponse(updatedPost) ;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        postRepository.delete(post);

    }

    @Override
    public List<PostDtoResponse> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category" , "id" , categoryId)
        ) ;
        List<Post> posts = postRepository.findByCategoryId(categoryId) ;
        return posts.stream().map(this::mapToPostDtoResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostDtoResponse> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream().map(this::mapToPostDtoResponse).collect(Collectors.toList());
    }



    private Post mapToEntity (PostDtoRequest postDtoRequest){

        return modelMapper.map(postDtoRequest, Post.class);
    }

    private PostDtoRequest mapToPostDtoRequest(Post post){
        return modelMapper.map(post , PostDtoRequest.class);
}
    private PostDtoResponse mapToPostDtoResponse(Post post){
        return modelMapper.map(post , PostDtoResponse.class);
    }
    private Post mapToPost(PostDtoResponse postDtoResponse){
        return modelMapper.map(postDtoResponse , Post.class);
    }
}
