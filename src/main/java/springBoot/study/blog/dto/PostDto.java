package springBoot.study.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class PostDto {

    private Long id ;
    @NotEmpty
    private String content;
    @NotEmpty
    @Size(min = 2 , message = "the title should be at least 2 characters")
    private String title ;
    @NotEmpty
    @Size(min = 10 , message = "description should be at least 10 characters")
    private String description ;
    private Set<CommentDto> comments ;


}
