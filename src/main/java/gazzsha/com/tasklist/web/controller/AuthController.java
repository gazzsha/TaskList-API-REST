package gazzsha.com.tasklist.web.controller;


import gazzsha.com.tasklist.domain.user.User;
import gazzsha.com.tasklist.service.AuthService;
import gazzsha.com.tasklist.service.UserService;
import gazzsha.com.tasklist.web.dto.auth.JwtRequest;
import gazzsha.com.tasklist.web.dto.auth.JwtResponse;
import gazzsha.com.tasklist.web.dto.user.UserDto;
import gazzsha.com.tasklist.web.dto.validation.OnCreate;
import gazzsha.com.tasklist.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(value = "/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(value = "/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping(value = "/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
