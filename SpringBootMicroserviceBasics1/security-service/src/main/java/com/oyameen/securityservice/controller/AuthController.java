package com.oyameen.securityservice.controller;
import com.oyameen.securityservice.model.LoginDto;
import com.oyameen.securityservice.model.User;
import com.oyameen.securityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody LoginDto loginDto) {
        return userService.register(loginDto);

    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return userService.verify(loginDto);
    }
}
