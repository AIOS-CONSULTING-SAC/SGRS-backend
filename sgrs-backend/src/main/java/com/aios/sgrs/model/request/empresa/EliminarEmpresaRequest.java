package com.aios.sgrs.model.request.empresa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EliminarEmpresaRequest {
    private Integer empresa;
    private String usuarioSesion;
    private String mensaje;

}
