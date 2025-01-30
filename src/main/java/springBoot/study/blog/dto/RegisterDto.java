package springBoot.study.blog.dto;

import jakarta.validation.constraints.Email;
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
public class RegisterDto {
    @NotEmpty(message = "username can not be empty")
    private String username ;
    @Email
    private String email ;
    @NotEmpty
    @Size(min = 8 , message = "password should be at least 8 characters")
    private String password ;
    @NotEmpty
    private String name ;

}
