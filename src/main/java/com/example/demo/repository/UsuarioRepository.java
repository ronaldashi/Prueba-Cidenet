package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

        Usuario findByNombreCompleto(String nombreCompleto);

        Usuario findByIdentificacion(String identificacion);

        boolean existsByIdentificacion(String identificacion);

        boolean existsByNombreCompleto(String nombreCompleto);

        @Query(value = "select u from Usuario u where (:nombreCompleto is null or u.nombreCompleto = :nombreCompleto)" +
                "and (:tipoDocumento is null or u.tipoDocumento = :tipoDocumento)" +
                "and (:identificacion is null or u.identificacion = :identificacion)")
        List<Usuario> findWithParametersIgnoreCase(@Param("nombreCompleto") String nombreCompleto, @Param("tipoDocumento") String tipoDocumento, @Param("identificacion") String identificacion);


}