package com.aios.sgrs.model.request.empresa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistrito;
    @NotBlank
    @NotEmpty
    private String direccion;
    private short idEstado;
    private Integer usuarioSesion;

    private String mensaje;

}
