package com.example.mockyclon.repositorio.security;

import com.example.mockyclon.encapsulaciones.sercurity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByUsername(String username);

    Usuario findUsuarioByUsernameAndPassword(String username, String password);

}
