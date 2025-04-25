package com.aios.sgrs.model.request.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GuardarUsuarioRequest {
    private String correo;
    private String password;
    private Integer idEmpresa;
    private String usuarioSesion;
    private String mensaje;
    private Integer idUsuario;
}
