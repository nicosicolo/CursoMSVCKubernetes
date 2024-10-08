package com.nsicolo.springcloud.msvc.usuarios.controllers;

import com.nsicolo.springcloud.msvc.usuarios.models.entity.Usuario;
import com.nsicolo.springcloud.msvc.usuarios.services.UsuariosService;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController//Combina response body y handler controller
public class UsuarioController {

    @Autowired
    private UsuariosService service;

    @GetMapping("/")
    public List<Usuario> listar(){
        return service.findAllUsers();
    }

    @GetMapping("/{id}") //VariablePath {variable}
    public ResponseEntity<?> detalle (@Valid @PathVariable Long id){//De forma automatica va a inyectar el id que llegue del url siempre que coincida con el nombre del argumento. Deben coincidar path variable y argumento del metodo.
        Optional<Usuario> opcional = service.findbyId(id);
        if(opcional.isPresent())
            return ResponseEntity.ok(opcional.get());
        return ResponseEntity.notFound().build();//Con build generamos la respuesta status 404 not found.
    }

    @PostMapping //ninguna ruta porque se envia en la raiz ya que solo queremos guardar
    //@ResponseStatus(HttpStatus.CREATED)//Status 201
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result){ //Con @RequestBody el body que enviemos en la peticion se va a convertir automaticamente en un objeto usuario.
        if(!usuario.getEmail().isEmpty() && service.findByEmailQuery(usuario.getEmail()).isPresent()){
            //Existe un usuario con ese mismo email
            return ResponseEntity.badRequest()
                    .body(Collections.
                            singletonMap("Mensaje", "Ya existe un usuario con ese mail."));
        }
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Usuario> opcional = service.findbyId(id);
        if (opcional.isPresent()){
            Usuario usuarioDb = opcional.get();
            if( !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                !usuario.getEmail().isEmpty() && service.findByEmail(usuario.getEmail()).isPresent()){
                //Existe un usuario con ese mismo email
                return ResponseEntity.badRequest()
                        .body(Collections.
                                singletonMap("Mensaje", "Ya existe un usuario con ese mail."));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario (@PathVariable Long id) {
        Optional<Usuario> opcional = service.findbyId(id);
        if(opcional.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>(); //Hago un map del tipo CampoDelError/Detalle del error.
        result.getFieldErrors().forEach(
                error -> {
                    errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
                }
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
