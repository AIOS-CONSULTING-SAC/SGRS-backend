package com.aios.sgrs.model.request.residuo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
public class GuardarResiduoRequest {
    private Integer residuo;
    private Integer codCliente;
    @NotBlank
    @NotEmpty
    private String descripcion;

    private Integer idUnidad;
    private short idEstado;

    private Integer usuarioSesion;

    private String mensaje;

}
