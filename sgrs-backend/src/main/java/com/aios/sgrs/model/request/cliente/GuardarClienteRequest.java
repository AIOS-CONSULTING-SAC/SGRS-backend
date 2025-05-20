package com.aios.sgrs.model.request.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuardarClienteRequest {
    private Integer cliente; // null si es nuevo
    private Integer codEmpresa;

    private String ruc;
    private String razonSocial;
    private String nombreComercial;

    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistrito;

    private String direccion;
    private Integer idEstado;
    private Integer usuarioSesion;
    private String mensaje;
}
