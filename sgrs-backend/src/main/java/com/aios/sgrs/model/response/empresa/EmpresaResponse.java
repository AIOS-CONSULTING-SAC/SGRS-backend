package com.aios.sgrs.model.response.empresa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class EmpresaResponse {
    private Integer empresa;
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String codigoUbigeo;
    private String idDepartamento;
    private String idProvincia;
    private String idDistrito;
    private String direccion;
    private short idEstado;
    private String descEstado;
    private Date fechaRegistro;
}
