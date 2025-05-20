package com.aios.sgrs.model.request.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EliminarClienteRequest {
    private Integer cliente;
    private Integer codEmpresa;
    private Integer usuarioSesion;

    private String mensaje;
}
