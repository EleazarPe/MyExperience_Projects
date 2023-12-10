package com.example.mockyclon;

import com.example.mockyclon.services.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MockyClonApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MockyClonApplication.class, args);
        UsuarioService usuarioService = (UsuarioService) applicationContext.getBean("usuarioService");
        usuarioService.initializeUsuario();
    }

}
