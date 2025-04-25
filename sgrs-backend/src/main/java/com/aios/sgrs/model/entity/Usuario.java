package com.aios.sgrs.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@ToString
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;
    private String apellidos;
    private String nroDocumento;
    private String correo;
    private String password;

    private Integer tipoUsuarioId;
    private Integer estado;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private UsuarioInterno usuarioInterno;

    // Getters, setters y helper para nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}