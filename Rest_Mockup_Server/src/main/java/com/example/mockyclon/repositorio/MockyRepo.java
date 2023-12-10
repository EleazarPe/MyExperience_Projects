package com.example.mockyclon.repositorio;

import com.example.mockyclon.encapsulaciones.Mockup;
import com.example.mockyclon.encapsulaciones.sercurity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MockyRepo extends JpaRepository<Mockup, Long> {
    List<Mockup> findAll();
    List<Mockup> findAllByUsuario(Usuario user);
    void deleteById(Long id);

    Optional<Mockup> findByCodigoAndNombre(String codigo,String nombre);

    Optional<Mockup> findById(Long id);
    //void updateBy(Mockup moki)

}
