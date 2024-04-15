package mx.edu.utez.lapaca.security.services.email;

import jakarta.mail.internet.MimeMessage;
import mx.edu.utez.lapaca.security.dto.email.EmailDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
public class EmailService {


    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    public boolean sendMail(EmailDto email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getEmail());
            helper.setSubject(email.getSubject());
            helper.setText(template(email), true);
            mailSender.send((message));
            return true;
        } catch (Exception e) {
            logger.error("Error al enviar el correo electrónico: {}", e.getMessage(), e);
            return false;
        }
    }

    public String template(EmailDto email) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>CarsiShop</title>
                <style>
                    body {
                        font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;
                        background-color: #ffffff;
                        margin: 0;
                        padding: 0;
                    }
                    
                    .container {
                        width: 100%;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    
                    .header {
                        background-color: #000000;
                        color: #ffffff;
                        text-align: center;
                        padding: 20px;
                        margin-bottom: 20px;
                    }
                    
                    .content {
                        padding: 20px;
                        background-color: #f9f9f9;
                        color: #000000;
                    }
                    
                    .footer {
                        background-color: #000000;
                        color: #ffffff;
                        text-align: center;
                        padding: 10px 0;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Soporte CarsiShop</h1>
                    </div>
                    <div class="content">
                        <p>%s</p>
                    </div>
                    <div class="footer">
                        <p>¡Gracias por elegir CarsiShop!</p>
                    </div>
                </div>
            </body>
            </html>
            """.replace("%s", email.getBody());
    }

}