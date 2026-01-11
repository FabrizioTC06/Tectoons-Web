package com.tectoons.tectoonsweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

//@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/usuario/registro", "/usuario/login", "/css/**", "/img/**").permitAll()
                
                // Rutas de administrador
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                
                // Rutas de artista
                .requestMatchers("/artista/**"/* ,"/obras/artista/**"*/).hasAnyAuthority("ROLE_ARTISTA", "ROLE_ADMIN")
                
                // Rutas de usuario (incluye artistas y admin)
                .requestMatchers("/usuario/**","/carrito").hasAnyAuthority("ROLE_USUARIO", "ROLE_ARTISTA", "ROLE_ADMIN")
                
                // Cualquier otra ruta abierta
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/usuario/login")
                .loginProcessingUrl("/usuario/login")
                .defaultSuccessUrl("/", true)
                .usernameParameter("identificador") // usuario o correo
                .passwordParameter("contrasena")
                .failureUrl("/usuario/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
