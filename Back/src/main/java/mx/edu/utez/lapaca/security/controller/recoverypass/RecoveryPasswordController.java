package mx.edu.utez.lapaca.security.controller.recoverypass;

import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.security.dto.email.EmailDto;
import mx.edu.utez.lapaca.security.dto.email.UpdatePasswordDto;
import mx.edu.utez.lapaca.security.services.JWTService;
import mx.edu.utez.lapaca.security.services.UserService;
import mx.edu.utez.lapaca.security.services.email.EmailService;
import mx.edu.utez.lapaca.security.services.impl.JWTServiceImpl;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;

import java.security.SecureRandom;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/recovery/")
@CrossOrigin(origins = {"*"})
public class RecoveryPasswordController {
    @Autowired
    private JWTServiceImpl provider;
    @Autowired
    private UsuarioService service;
    @Autowired
    private EmailService emailService;

    //String que se envia al email
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Map<String, Object>>> getGeneratedResetPasswordToken(
            @Valid @RequestBody EmailDto emailDto) {
        Usuario user = service.getUserByEmail(emailDto.getEmail());
        if (user != null) {
            String resetPasswordToken = provider.generateResetPasswordToken(emailDto.getEmail());
            String secretPass = generateRandomString();
            service.updateSecretPass(user, secretPass);
            Map<String, Object> data = new HashMap<>();
            data.put("reset_password_token", resetPasswordToken);
            data.put("user", user);
            data.put("secretPass", secretPass);
            emailService.sendMail(new EmailDto(
                    user.getEmail(),
                    user.getNombre() + " " ,
                    "Soporte CarsiShop | Reestablecimiento de contraseña",
                    "Tu código de verificación es: " + secretPass + "<br>No lo compartas con nadie"
            ));
            return new ResponseEntity<>(
                    new CustomResponse<>(
                            data,
                            false,
                            200,
                            "Ok"
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Usuario no existente"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @PutMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse<Usuario>> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto,
            @Valid BindingResult result) {
        Usuario user = service.getUserByEmail(updatePasswordDto.getEmail());
        if (user != null) {
            if (result.hasErrors()) {
                return new ResponseEntity<>(
                        null,
                        HttpStatus.BAD_REQUEST
                );
            }
            if (updatePasswordDto.getSecretPass().equals(user.getSecretPass())) {
                user.setPassword(updatePasswordDto.getNewPassword());
                service.updateSecretPass(user, null);
                emailService.sendMail(new EmailDto(
                        user.getEmail(),
                        user.getNombre() + " " ,
                        "Soporte CarsiShop | Cambio de contraseña",
                        "Se ha actualizado correctamente la contraseña de tu cuenta para el sistema CarsiShop"
                ));
                return new ResponseEntity<>(
                        this.service.updatePassword(user),
                        HttpStatus.CREATED
                );
            } else {
                return new ResponseEntity<>(
                        new CustomResponse<>(
                                null,
                                true,
                                400,
                                "El código de verificación no válido."
                        ),
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        return new ResponseEntity<>(
                new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Usuario no existente"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public static String generateRandomString() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}