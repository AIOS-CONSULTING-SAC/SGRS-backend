package com.aios.sgrs.model.request.residuo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EliminarResiduoRequest {
    private Integer residuo;
    private Integer usuarioSesion;
    private String mensaje;

}
