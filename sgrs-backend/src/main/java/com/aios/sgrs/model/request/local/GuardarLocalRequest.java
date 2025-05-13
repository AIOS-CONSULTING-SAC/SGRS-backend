package com.aios.sgrs.model.request.local;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GuardarLocalRequest {
    private Integer local;
    private Integer codCliente;
    @NotBlank
    @NotEmpty
    private String nombre;
    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistrito;
    private String direccion;

    private short idEstado;
    private Integer usuarioSesion;

    private String mensaje;

}
