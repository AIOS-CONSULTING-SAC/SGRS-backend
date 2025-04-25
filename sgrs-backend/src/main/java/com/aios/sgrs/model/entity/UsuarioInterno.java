package com.aios.sgrs.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_internos")
public class UsuarioInterno {

    @Id
    private Integer usuarioId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Integer rolId;
    private Integer estado;
}
