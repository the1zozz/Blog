package springBoot.study.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
   @NotEmpty(message = "name can not be empty")
    private String name;
    @NotEmpty
    @Size(min = 10 , message = "description should be at least 10 characters")
    private String description;
}
