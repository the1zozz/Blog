package springBoot.study.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    description = "PostDto model information"
)

public class PostDto {

    private Long id ;
    @NotEmpty
    @Schema(
        description = "the content of the post"
    )
    private String content;
    @NotEmpty
    @Schema(
            description = "the title of the post"
    )
    @Size(min = 2 , message = "the title should be at least 2 characters")
    private String title ;
    @NotEmpty
    @Schema(
            description = "the description of the post"
    )
    @Size(min = 10 , message = "description should be at least 10 characters")
    private String description ;
    private Set<CommentDto> comments ;
    @Schema(
            description = "Post category id"
    )
    private long categoryId ;


}
