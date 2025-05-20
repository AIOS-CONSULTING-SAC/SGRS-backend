package com.aios.sgrs.model.request.parametro;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EliminarParametroRequest {
    private Integer parametro;
    private Integer usuarioSesion;

    private String mensaje;
}
