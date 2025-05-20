package com.aios.sgrs.model.response.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ClienteResponse {
    private Integer cliente;
    private Integer codEmpresa;
    private String ruc;
    private String razonSocial;
    private String nombreComercial;

    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistrito;

    private String direccion;
    private Short idEstado;
    private String descEstado;

    private Date fechaRegistro;
}
