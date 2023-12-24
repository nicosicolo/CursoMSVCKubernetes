package com.nsicolo.springcloud.msvc.usuarios.services;

import com.nsicolo.springcloud.msvc.usuarios.models.entity.Usuario;
import com.nsicolo.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService{

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    @Transactional(readOnly = true)//Del package de spring.
    public List<Usuario> findAllUsers() {
        return (List<Usuario>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findbyId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}
