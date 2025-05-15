package springBoot.study.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDtoResponse {
    private Long id ;
    private String content;
    private String title ;
    private String description ;
    private Set<CommentDto> comments ;
    private long categoryId ;
}
