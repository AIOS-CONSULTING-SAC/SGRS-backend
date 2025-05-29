package com.aios.sgrs.model.response.residuo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResiduoResponse {
    private Integer residuo;
    private Integer codCliente;
    private String descripcion;
    private Integer idUnidad;
    private String descUnidad;
    private short idEstado;
    private String descEstado;
    private Date fechaRegistro;

}
