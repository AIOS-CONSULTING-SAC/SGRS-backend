package com.aios.sgrs.model.request.residuo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EliminarResiduoRequest {
    private Integer residuo;
    private String usuarioSesion;
    private String mensaje;

}
