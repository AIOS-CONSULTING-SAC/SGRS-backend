package com.aios.sgrs.model.request.local;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EliminarLocalRequest {
    private Integer local;
    private Integer usuarioSesion;
    private String mensaje;

}
