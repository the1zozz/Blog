package springBoot.study.blog.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springBoot.study.blog.dto.LoginDto;
import springBoot.study.blog.dto.RegisterDto;
import springBoot.study.blog.exception.BlogAPIException;
import springBoot.study.blog.models.Role;
import springBoot.study.blog.models.User;
import springBoot.study.blog.repository.RoleRepository;
import springBoot.study.blog.repository.UserRepository;
import springBoot.study.blog.security.JwtTokenProvider;
import springBoot.study.blog.services.LoginAuthService;

import java.util.HashSet;
import java.util.Set;

@Service
public class LoginAuthServiceImpl implements LoginAuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public LoginAuthServiceImpl(AuthenticationManager authenticationManager ,
                                UserRepository userRepository,
                                RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder ,
                                JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.createToken(authenticate);

        return token;
    }

    @Override
    public String signUp(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Email Already Exists");
        }
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Username Already Exists");
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return " User Registered Successfully";
    }

}
