package com.example.demo.service;

import com.example.demo.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> getAll(String nombreCompleto,
                         String tipoDocumento,
                         String identificacion,
                         int page);
    Usuario findByNombreCompleto(String nombreCompleto);
    Usuario findByIdentificacion(String identificacion);
    Usuario saveuser(Usuario user)throws Exception;
    boolean existByIdentificacion(String identificacion);

    Usuario updateuser(Usuario user, int id)throws Exception;

}
