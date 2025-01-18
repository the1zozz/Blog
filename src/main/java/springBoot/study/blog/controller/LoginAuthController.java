package springBoot.study.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springBoot.study.blog.dto.LoginDto;
import springBoot.study.blog.dto.RegisterDto;
import springBoot.study.blog.services.LoginAuthService;

@RestController
@RequestMapping("/api/auth")
public class LoginAuthController {

    private LoginAuthService loginAuthService;

    public LoginAuthController(LoginAuthService loginAuthService) {
        this.loginAuthService = loginAuthService;
    }
    @PostMapping(value = {"/login" , "/signin"})
    public ResponseEntity<String> login( @RequestBody LoginDto loginDto) {
        String response = loginAuthService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = {"signup" , "register"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = loginAuthService.signUp(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
