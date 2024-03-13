package mx.edu.utez.lapaca.security.services;


import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.security.dto.JwtAuthenticationResponse;
import mx.edu.utez.lapaca.security.dto.RefreshTokenRequest;
import mx.edu.utez.lapaca.security.dto.SignUpRequest;


public interface AuthenticationService {
    Usuario singupUser(SignUpRequest signUpRequest);
    Usuario singupAdmin(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignUpRequest signUpRequest);

    JwtAuthenticationResponse jwtAuthenticationResponse(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
