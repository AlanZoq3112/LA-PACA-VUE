package mx.edu.utez.lapaca.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.security.dto.JwtAuthenticationResponse;
import mx.edu.utez.lapaca.security.dto.RefreshTokenRequest;
import mx.edu.utez.lapaca.security.dto.SignUpRequest;
import mx.edu.utez.lapaca.security.dto.SinginRequest;
import mx.edu.utez.lapaca.security.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080", "http://localhost:5173"})
@RestController
@RequestMapping("/api-carsi-shop/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/singupUser")
    public ResponseEntity<Usuario> singupUser(@Valid @RequestBody SignUpRequest signUpRequest){
        return  ResponseEntity.ok(authenticationService.singupUser(signUpRequest));
    }

    @PostMapping("/singupAdmin")
    public ResponseEntity<Usuario> singupAdmin(@Valid @RequestBody SignUpRequest signUpRequest){
        return  ResponseEntity.ok(authenticationService.singupAdmin(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SinginRequest singinRequest){
        return ResponseEntity.ok(authenticationService.signin(singinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
