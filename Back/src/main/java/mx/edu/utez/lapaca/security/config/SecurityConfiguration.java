package mx.edu.utez.lapaca.security.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.security.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api-carsi-shop/auth/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/admin/usuario/insert").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/admin/usuario/getAll").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/admin/usuario/getOne").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name(), Role.COMPRADOR.name())
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/admin/usuario/update").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api-carsi-shop/admin/usuario/delete").hasAnyAuthority(Role.ADMIN.name())

                        .requestMatchers("/api-carsi-shop/admin/categoria/**").hasAnyAuthority(Role.ADMIN.name())




                        .requestMatchers("/api-carsi-shop/usuario/**").hasAnyAuthority(
                                Role.ADMIN.name(), Role.VENDEDOR.name(), Role.COMPRADOR.name())
                        .anyRequest().authenticated())



                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        }))


                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

                return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}