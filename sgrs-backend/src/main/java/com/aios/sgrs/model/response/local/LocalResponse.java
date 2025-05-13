package com.aios.sgrs.model.response.local;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalResponse {
    private Integer local;
    private Integer codCliente;
    private String nombre;
    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistrito;
    private String direccion;
    private short idEstado;
    private String descEstado;
    private Date fechaRegistro;

}
