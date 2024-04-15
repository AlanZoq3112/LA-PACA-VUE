package mx.edu.utez.lapaca.services.captcha.controller;

import mx.edu.utez.lapaca.services.captcha.dto.CaptchaResponse;
import mx.edu.utez.lapaca.services.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = {"http://localhost:8091", "http://localhost:8080", "http://localhost:5173"},
        methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)
@RequestMapping("/api-carsi-shop/captcha")
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/verificar-captcha")
    public CaptchaResponse verificarCaptcha(@RequestParam("solution") String solution) {
        return captchaService.verificarCaptcha(solution);
    }
}
