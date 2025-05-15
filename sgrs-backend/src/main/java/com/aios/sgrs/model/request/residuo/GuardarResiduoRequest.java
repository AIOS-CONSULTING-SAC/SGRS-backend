package com.aios.sgrs.model.request.residuo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
