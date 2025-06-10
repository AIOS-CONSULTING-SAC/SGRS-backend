package com.aios.sgrs.model.response.manejoResiduo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManejoResiduoResponse {
    private Integer codCliente;
    private String descCliente;
    private Integer codLocal;
    private String descLocal;
    private Integer codResiduo;
    private String descResiduo;
    private Integer codUnidad;
    private String descUnidad;
    private Double mes01;
    private Double mes02;
    private Double mes03;
    private Double mes04;
    private Double mes05;
    private Double mes06;
    private Double mes07;
    private Double mes08;
    private Double mes09;
    private Double mes10;
    private Double mes11;
    private Double mes12;
    private Double total;
}
