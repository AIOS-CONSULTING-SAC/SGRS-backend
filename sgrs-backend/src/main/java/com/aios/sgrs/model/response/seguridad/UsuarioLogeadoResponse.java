package com.aios.sgrs.model.response.seguridad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioLogeadoResponse {
    private int resultado;
    private String mensaje;
    private int usuarioId;
    private String nombres;
    private String apellidos;
    private String password;
    private int tipoUsuarioId;
    private Integer rolId;
    private Integer empresaId;
}
