package com.nsicolo.springcloud.msvc.usuarios.services;

import com.nsicolo.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    List<Usuario> findAllUsers();
    Optional<Usuario> findbyId(Long id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailQuery(String email);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existeEmail(String email);
}
