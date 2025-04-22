package com.aios.sgrs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AuditoriaResponse {
    private int estadoRegistro;
    private String desEstadoRegistro;
    private String creacionSesion;
    private String creacionFecha;
    private String controlSesion;
    private String controlFecha;
}
