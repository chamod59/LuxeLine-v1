package com.chamo.BackEnd.services;

import com.chamo.BackEnd.dto.LoginReqDto;
import com.chamo.BackEnd.dto.LoginResDto;
import com.chamo.BackEnd.dto.RegisterReqDto;
import com.chamo.BackEnd.dto.RegisterResDto;
import com.chamo.BackEnd.entity.UserEntity;
import com.chamo.BackEnd.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserEntity> getAllUsers(){
        return  userRepository.findAll();
    }

    public UserEntity createUser(RegisterReqDto userData){
        UserEntity newUser = new UserEntity(userData.getName(),
                userData.getEmail(),
                userData.getUsername(),
                passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(newUser);
    }

    public LoginResDto login(LoginReqDto loginData){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(),loginData.getPassword()));
        }catch (Exception e){
            return new LoginResDto(null, null, "User Not Found!", "Login Error");
        }

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("role","User");
        claims.put("email","company@gmail.com");

        String token = jwtService.getJWTToken(loginData.getUsername(), claims);
        System.out.println(jwtService.getFieldFromToken(token,"role"));
        return new LoginResDto(token, LocalDateTime.now(),null,"Token generated successfully..");
    }

    public RegisterResDto register(RegisterReqDto registerData){
        if(isUserActive(registerData.getUsername())) return new RegisterResDto(null, "User already exists!");

        var userData = this.createUser(registerData);
        if(userData.getId() == null) return new RegisterResDto(null, "system error!");

        return new RegisterResDto(String.format("user registered at %s", userData.getId()),null);
    }

    private Boolean isUserActive(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
