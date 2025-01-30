package springBoot.study.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springBoot.study.blog.dto.PostDto;
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
   private ModelMapper modelMapper ;
   private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper , CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper ;
        this.categoryRepository = categoryRepository ;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category" , "id" , postDto.getCategoryId())) ;
        Post post = mapToEntity(postDto) ;
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        return mapToPostDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending() ;
        Pageable pageable = PageRequest.of(pageNo , pageSize , sort ) ;

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());
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
    public PostDto getPostById(long id) {  
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        return mapToPostDto(post) ;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("Category" ,"id", postDto.getCategoryId())
        ) ;
        post.setCategory(category);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post) ;
        return mapToPostDto(updatedPost) ;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , id)) ;
        postRepository.delete(post);

    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category" , "id" , categoryId)
        ) ;
        List<Post> posts = postRepository.findByCategoryId(categoryId) ;
        return posts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());
    }

    private Post mapToEntity (PostDto postDto){
        Post post = modelMapper.map(postDto , Post.class);

        return post ;
    }

    private PostDto mapToPostDto(Post post){
       PostDto postResponse = modelMapper.map(post , PostDto.class);
       return postResponse ;
}
}
