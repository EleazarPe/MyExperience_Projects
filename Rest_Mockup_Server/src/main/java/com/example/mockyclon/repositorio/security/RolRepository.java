package com.example.mockyclon.repositorio.security;

import com.example.mockyclon.encapsulaciones.sercurity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, String> {

    Rol findByRole(String rol);
}
