package com.aios.sgrs.model.request.seguridad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UsuarioRequest {
    @NotNull(message = "El usuario es requerido")
    @NotEmpty(message = "El usuario es requerido")
    private String usuario;
    @NotEmpty(message = "La contraseña es requerido")
    @NotNull(message = "La contraseña es requerida")
    private String password;
    private String ip;
}
