package mx.edu.edu.loginjwt;

import mx.edu.edu.loginjwt.entity.Role;
import mx.edu.edu.loginjwt.entity.User;
import mx.edu.edu.loginjwt.repositiry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoginJwtApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginJwtApplication.class, args);
	}


}
