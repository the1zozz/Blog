package springBoot.study.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommentDto {
    private Long id ;
    @NotEmpty(message = "name can not be empty")
    private String name ;
    @Email
    private String email ;
    @NotEmpty
    @Size(min = 10 , message = "body should be 10 characters at least")
    private String body ;
}
