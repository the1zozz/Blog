package springBoot.study.blog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @Email
    private String usernameOrEmail;
    @NotEmpty(message = "password can not be empty")
    @Size(min = 8 , message = "password should be at least 8 characters")
    private String password;
}
