package mx.edu.edu.loginjwt.services;

import mx.edu.edu.loginjwt.dto.JwtAuthenticationResponse;
import mx.edu.edu.loginjwt.dto.RefreshTokenRequest;
import mx.edu.edu.loginjwt.dto.SignUpRequest;
import mx.edu.edu.loginjwt.entity.User;

public interface AuthenticationService {
    User singupUser(SignUpRequest signUpRequest);
    User singupAdmin(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignUpRequest signUpRequest);

    JwtAuthenticationResponse jwtAuthenticationResponse(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
