package com.PTIT.QuanLyThuVien.controller;

import com.PTIT.QuanLyThuVien.error.UserNotFoundException;
import com.PTIT.QuanLyThuVien.model.User;
import com.PTIT.QuanLyThuVien.payload.request.LoginRequest;
import com.PTIT.QuanLyThuVien.payload.request.SignupRequest;
import com.PTIT.QuanLyThuVien.payload.response.LoginResponse;
import com.PTIT.QuanLyThuVien.payload.response.MessageResponse;
import com.PTIT.QuanLyThuVien.security.jwt.JwtUtils;
import com.PTIT.QuanLyThuVien.security.services.CustomUserDetails;
import com.PTIT.QuanLyThuVien.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:1234/")
@RequestMapping("/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername(),
                userDetails.getFirstName(), userDetails.getLastName()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
