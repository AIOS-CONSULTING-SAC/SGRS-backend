package com.aios.sgrs.model.response.configuracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametroResponse  {
    private Integer codigoInt;
    private String codigo;
    private String descripcion;
    private String abreviatura;
    private Integer estado;
    private String descEstado;
    private String descFechaRegistro;
    private Date fechaRegistro;
}
