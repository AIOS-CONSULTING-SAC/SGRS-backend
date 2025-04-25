package com.aios.sgrs.model.request.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GuardarEmpresaRequest {
    private Integer empresa;
    @NotBlank
    @NotEmpty
    private String ruc;
    @NotBlank
    @NotEmpty
    private String razonSocial;
    @NotBlank
    @NotEmpty
    private String nombreComercial;
    private String idDepartamento;
    private String idProvincia;
    private String idDistrito;
    @NotBlank
    @NotEmpty
    private String direccion;
    private short idEstado;
    private String usuarioSesion;

    private String mensaje;

}
