package com.nsicolo.springcloud.msvc.usuarios.repositories;

import com.nsicolo.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

//Tengo que incluir en el crudRepository la clase y el tipo del id.
//La clase crudRepository ya esta integrada en spring, por lo tanto ya podriamos inyectar en otras clases.
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}




