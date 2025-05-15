package com.aios.sgrs.model.request.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GuardarUsuarioRequest {
    private Integer idUsuario;
    private Integer idEmpresa;
    private Integer idcliente;
    private Integer idTipoUser;
    private Integer idPerfil;
    private Integer idTipoDoc;
    private String ndoc;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String correo;
    private String password;
    private short idEstado;
    private Integer usuarioSesion;
    private String mensaje;

}
