package com.example.mockyclon.config;

import com.example.mockyclon.services.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecurity {

    //Opción JPA
    private SecurityService seguridadServices;
    private PasswordEncoder passwordEncoder;

    public ConfigSecurity(SecurityService seguridadServices, PasswordEncoder passwordEncoder) {
        this.seguridadServices = seguridadServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        //Configuración JDBC
        /*System.out.println("Autentificación JDBC");
        auth.jdbcAuthentication().usersByUsernameQuery(queryUsuario)
                .authoritiesByUsernameQuery(queryRol)
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource);*/

        //Servicio.
        System.out.println("Autentificación en JPA");
        auth.userDetailsService(seguridadServices)
                .passwordEncoder(passwordEncoder);


        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/mockycrud/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/usuario/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**")))
                .authorizeHttpRequests(authorization -> authorization
                        //.requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(mvc.pattern("/mockycrud")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(mvc.pattern("/mockycrud/**")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(mvc.pattern("/usuario/lista")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(mvc.pattern("/usuario/crear")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/usuario/crearPost")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/usuario/editar/**")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/usuario/editarPost")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/usuario/eliminar/**")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern("/usuario/lista/**")).hasAnyRole("ADMIN","USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).hasAnyRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/console/**")).hasAnyRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/favicon")).permitAll()
                )
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin((form) -> form
                        .failureUrl("/login?error") //en caso de fallar puedo indicar otra pagina.
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .permitAll());
        return http.build();
    }
}
