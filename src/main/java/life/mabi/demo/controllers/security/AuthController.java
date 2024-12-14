package life.mabi.demo.controllers.security;

import life.mabi.demo.dto.security.AuthResponseDto;
import life.mabi.demo.dto.security.LoginDto;
import life.mabi.demo.dto.security.UserDto;
import life.mabi.demo.jwt.JWTGenerator;
import life.mabi.demo.models.security.RoleEntity;
import life.mabi.demo.models.security.UserEntity;
import life.mabi.demo.repository.security.RoleRepository;
import life.mabi.demo.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    //add authentication dependencies
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("이 Username은 이미 사용 중입니다.!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode((userDto.getPassword())));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        RoleEntity roles = roleRepository.findByName(userDto.getRole())
                .orElseGet(() -> {
                    System.out.println("Role 없음");
                    RoleEntity role = new RoleEntity();
                    role.setName(userDto.getRole());
                    return roleRepository.save(role);
                });
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return new ResponseEntity<>("유저가 등록되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //generate token
        String token = jwtGenerator.generateToken(authentication);

        AuthResponseDto authResponseDto = new AuthResponseDto(token);
        authResponseDto.setUsername(loginDto.getUsername());

        Optional<UserEntity> optionalUser = userRepository.findByUsername(loginDto.getUsername());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            authResponseDto.setRole(userEntity.getRoles().get(0).getName());
        }
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
