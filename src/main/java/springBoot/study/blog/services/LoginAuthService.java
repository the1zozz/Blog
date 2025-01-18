package springBoot.study.blog.services;

import springBoot.study.blog.dto.LoginDto;
import springBoot.study.blog.dto.RegisterDto;

public interface LoginAuthService {
     String login(LoginDto loginDto);
     String signUp(RegisterDto registerDto);

}
