package mx.edu.utez.lapaca.security.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;

import mx.edu.utez.lapaca.security.dto.JwtAuthenticationResponse;
import mx.edu.utez.lapaca.security.dto.RefreshTokenRequest;
import mx.edu.utez.lapaca.security.dto.SignUpRequest;
import mx.edu.utez.lapaca.security.dto.SinginRequest;
import mx.edu.utez.lapaca.security.services.AuthenticationService;
import mx.edu.utez.lapaca.security.services.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsuarioRepository userRepository;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final PasswordEncoder passwordEncoder;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;




    public Usuario singupUser(SignUpRequest signUpRequest){
        Usuario user = new Usuario();
        user.setNombre(signUpRequest.getNombre());
        user.setGenero(signUpRequest.getGenero());
        user.setImagenUrl(signUpRequest.getImagenUrl());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setTelefono(signUpRequest.getTelefono());
        user.setFechaNacimiento(signUpRequest.getFechaNacimiento());
        user.setRole(Role.COMPRADOR);
        return userRepository.save(user);
    }

    public Usuario singupAdmin(SignUpRequest signUpRequest){
        Usuario user = new Usuario();
        user.setNombre(signUpRequest.getNombre());
        user.setGenero(signUpRequest.getGenero());
        user.setImagenUrl(signUpRequest.getImagenUrl());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setTelefono(signUpRequest.getTelefono());
        user.setFechaNacimiento(signUpRequest.getFechaNacimiento());
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SinginRequest singinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singinRequest.getEmail(),
                singinRequest.getPassword()));
        var user = userRepository.findByEmail(singinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse jwtAuthenticationResponse(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        Usuario user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
