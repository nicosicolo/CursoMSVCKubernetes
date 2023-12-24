package com.nsicolo.springcloud.msvc.usuarios.services;

import com.nsicolo.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    List<Usuario> findAllUsers();
    Optional<Usuario> findbyId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
}
