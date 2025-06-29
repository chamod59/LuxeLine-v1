package com.chamo.BackEnd.services;

import com.chamo.BackEnd.dto.auth.LoginReqDto;
import com.chamo.BackEnd.dto.auth.LoginResDto;
import com.chamo.BackEnd.dto.auth.RegisterReqDto;
import com.chamo.BackEnd.dto.auth.RegisterResDto;
import com.chamo.BackEnd.entity.UserEntity;
import com.chamo.BackEnd.exception.InvalidInputException;
import com.chamo.BackEnd.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public RegisterResDto register(RegisterReqDto registerData) {
        try {
            // Validate input data
            validateRegisterData(registerData);

            // Check for existing user
            if (userRepository.findByUsername(registerData.getUsername()).isPresent()) {
                throw new InvalidInputException("USERNAME_EXISTS", "Username already taken");
            }
            if (userRepository.findByUsername(registerData.getEmail()).isPresent()) {
                throw new InvalidInputException("EMAIL_EXISTS", "Email already registered");
            }

            // Create and save user
            UserEntity user = createUser(registerData);

            return new RegisterResDto(
                    "User registered successfully with ID: " + user.getId(),
                    null
            );

        } catch (InvalidInputException e) {
            return new RegisterResDto(
                    null,
                    e.getErrorCode() + ": " + e.getMessage()
            );
        } catch (Exception e) {
            return new RegisterResDto(
                    null,
                    "REGISTRATION_FAILED: " + e.getMessage()
            );
        }
    }

    public LoginResDto login(LoginReqDto loginData) {
        try {
            // Validate input
            validateLoginData(loginData);

            // Authenticate
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginData.getUsername(),
                            loginData.getPassword()
                    )
            );

            // Prepare claims
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "User");
            claims.put("email", loginData.getUsername() + "@example.com");

            // Generate token
            String token = jwtService.getJWTToken(loginData.getUsername(), claims);

            return new LoginResDto(
                    token,
                    LocalDateTime.now(),
                    null,
                    "Login successful. Welcome back!"
            );

        } catch (BadCredentialsException e) {
            return new LoginResDto(
                    null,
                    null,
                    "AUTHENTICATION_FAILED",
                    "Invalid credentials. Please check your username and password."
            );
        } catch (InvalidInputException e) {
            return new LoginResDto(
                    null,
                    null,
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            return new LoginResDto(
                    null,
                    null,
                    "SERVER_ERROR",
                    "Login service unavailable. Please try again later."
            );
        }
    }

    public UserEntity createUser(RegisterReqDto userData) {
        UserEntity newUser = new UserEntity(
                userData.getName(),
                userData.getEmail(),
                userData.getUsername(),
                passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(newUser);
    }

    private void validateRegisterData(RegisterReqDto registerData) {
        validateUserData(registerData);

        if (userRepository.findByUsername(registerData.getUsername()).isPresent()) {
            throw new InvalidInputException("Username already exists");
        }

        if (userRepository.findByUsername(registerData.getEmail()).isPresent()) {
            throw new InvalidInputException("Email already registered");
        }
    }

    private void validateUserData(RegisterReqDto userData) {
        if (!StringUtils.hasText(userData.getName())) {
            throw new InvalidInputException("Name cannot be empty");
        }

        if (!StringUtils.hasText(userData.getEmail()) || !EMAIL_PATTERN.matcher(userData.getEmail()).matches()) {
            throw new InvalidInputException("Invalid email format");
        }

        if (!StringUtils.hasText(userData.getUsername()) || !USERNAME_PATTERN.matcher(userData.getUsername()).matches()) {
            throw new InvalidInputException("Username must be 3-20 characters (letters, numbers, ., _, -)");
        }

        if (!StringUtils.hasText(userData.getPassword()) || !PASSWORD_PATTERN.matcher(userData.getPassword()).matches()) {
            throw new InvalidInputException("Password must be 8+ chars with uppercase, lowercase, number, and special character");
        }
    }

    private void validateLoginData(LoginReqDto loginData) {
        if (!StringUtils.hasText(loginData.getUsername())) {
            throw new InvalidInputException("Username cannot be empty");
        }

        if (!StringUtils.hasText(loginData.getPassword())) {
            throw new InvalidInputException("Password cannot be empty");
        }
    }
}