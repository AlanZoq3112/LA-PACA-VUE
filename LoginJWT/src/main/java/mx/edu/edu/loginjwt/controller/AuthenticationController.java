package mx.edu.edu.loginjwt.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.edu.loginjwt.dto.JwtAuthenticationResponse;
import mx.edu.edu.loginjwt.dto.RefreshTokenRequest;
import mx.edu.edu.loginjwt.dto.SignUpRequest;
import mx.edu.edu.loginjwt.entity.User;
import mx.edu.edu.loginjwt.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/singup")
    public ResponseEntity<User> singup(@RequestBody SignUpRequest signUpRequest){
        return  ResponseEntity.ok(authenticationService.singup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signin(signUpRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
