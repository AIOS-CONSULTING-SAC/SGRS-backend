package com.aios.sgrs.model.request.parametro;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuardarParametroRequest {

    private Integer parametro; // null si es nuevo
    private Integer codModulo;
    private String descModulo;
    private Integer codOpcion;
    private String descOpcion;
    private Integer prefijo;
    private Integer correlativo;
    private String desc01;
    private String desc02;
    private String desc03;
    private Integer int01;
    private Integer int02;
    private Short idEstado;
    private Integer usuarioSesion;
    private String mensaje;
}
