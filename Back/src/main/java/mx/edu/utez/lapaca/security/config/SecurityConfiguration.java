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
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api-carsi-shop/auth/**").permitAll()
                        .requestMatchers("/api-carsi-shop/email/**").permitAll()
                        .requestMatchers("/api-carsi-shop/recovery/**").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/usuario/insert").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/usuario/getAll").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/usuario/getOne").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name(), Role.COMPRADOR.name())
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/usuario/update").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api-carsi-shop/usuario/delete").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/vendedor/insert").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/vendedor/getAll").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/vendedor/getOne").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/vendedor/aprobarSolicitudVendedor").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/vendedor/update").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())

                        .requestMatchers("/api-carsi-shop/categoria/insert").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api-carsi-shop/subcategoria/getAll").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers("/api-carsi-shop/subcategoria/getOne").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers("/api-carsi-shop/subcategoria/update").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api-carsi-shop/subcategoria/delete").hasAuthority(Role.ADMIN.name())

                        .requestMatchers("/api-carsi-shop/subcategoria/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/producto/insert").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/producto/getAll").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/producto/getOne").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/producto/aprobarSolicitudProducto").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/producto/update").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/producto/mis-productos").hasAnyAuthority(Role.ADMIN.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/producto/productos-aprobados").permitAll()


                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/direccion/insert").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/direccion/getAll").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/direccion/getOne").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/direccion/mis-direcciones").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.PUT,"/api-carsi-shop/direccion/update").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.DELETE,"/api-carsi-shop/direccion/delete").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())

                        .requestMatchers(HttpMethod.POST,"api-carsi-shop/pago/insertarFormaPago").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/pago/getAllMetodosPago").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/pago/mis-metodos-pago").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())



                        .requestMatchers(HttpMethod.POST,"api-carsi-shop/pago/realizar-pago").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/pago/getAllPedidos").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/pago/mis-pedidos").hasAnyAuthority(Role.ADMIN.name(), Role.COMPRADOR.name(), Role.VENDEDOR.name())

                        .requestMatchers("/api-carsi-shop/log/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"api-carsi-shop/log/getAllLogs").hasAuthority(Role.ADMIN.name())

                        .requestMatchers("/api-carsi-shop/loglogin/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api-carsi-shop/loglogin/getAllLogslogin").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api-carsi-shop/loglogin/getAllLogslogin").permitAll()

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
