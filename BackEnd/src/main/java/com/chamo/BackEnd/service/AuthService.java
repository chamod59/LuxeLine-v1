//package com.chamo.BackEnd.service;
//
//import com.chamo.BackEnd.dto.AuthRequest;
//import com.chamo.BackEnd.dto.AuthResponse;
//import com.chamo.BackEnd.entity.UserEntity;
//import com.chamo.BackEnd.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JWTService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthResponse register(AuthRequest request) {
//        UserEntity user = new UserEntity();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
//
//        UserDetails userDetails = User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//
//        String jwtToken = jwtService.generateToken(userDetails);
//        return new AuthResponse(jwtToken);
//    }
//
//    public AuthResponse login(AuthRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        UserEntity user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        UserDetails userDetails = User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//
//        String jwtToken = jwtService.generateToken(userDetails);
//        return new AuthResponse(jwtToken);
//    }
//}


package com.chamo.BackEnd.service;

import com.chamo.BackEnd.dto.AuthRequest;
import com.chamo.BackEnd.dto.AuthResponse;
import com.chamo.BackEnd.entity.UserEntity;
import com.chamo.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        String jwtToken = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        String jwtToken = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }
}
