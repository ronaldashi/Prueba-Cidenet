package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "USUARIO_ID_GENERATOR", sequenceName = "USUARIO_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USUARIO_ID_GENERATOR")
    @Column
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @NotBlank(message = "nombreCompleto es obligatorio")
    //@Size(min=3,max=20,message ="The nombreCompleto cant be more than {max} characters and less than {min}")
    @Column
    private String nombreCompleto;

    @NotNull
    @NotBlank(message = "tipoDocumento es obligatorio")
    //@Size(min=3,max=20,message ="The tipoDocumento cant be more than 20 characters and less than 3")
    @Column
    private String tipoDocumento;

    @NotNull
    @NotBlank(message = "identificacion es obligatorio")
    //@Size(min=3,max=20,message ="The tipoDocumento cant be more than 20 characters and less than 3")
    @Column
    private String identificacion;

}

