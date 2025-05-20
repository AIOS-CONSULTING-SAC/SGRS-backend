package com.aios.sgrs.model.request.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EliminarUsuarioRequest {
    private Integer usuario;
    private Integer usuarioSesion;
    private String mensaje;

}
