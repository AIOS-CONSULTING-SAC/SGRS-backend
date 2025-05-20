package com.aios.sgrs.model.response.parametro;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametroResponse {

    private Integer parametro;
    private Integer codModulo;
    private String descModulo;
    private Integer codOpcion;
    private String descOpcion;
    private Integer prefijo;
    private Integer correlativo;
    private String descripcion1;
    private String descripcion2;
    private String descripcion3;
    private Integer entero01;
    private Integer entero02;
    private short idEstado;
    private String descEstado;
    private Integer codUserReg;
    private Date fechaRegistro;

}
