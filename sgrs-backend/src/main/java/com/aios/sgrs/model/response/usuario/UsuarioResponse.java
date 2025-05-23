package com.aios.sgrs.model.response.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResponse {
    private Integer usuario;
    private Integer codEmpresa;
    private String descEmpresa;
    private Integer codCliente;
    private String descCliente;
    private Integer codTipoUser;
    private String descTipoUser;
    private Integer codPerfil;
    private String descPerfil;
    private Integer codTipoDoc;
    private String ndoc;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String telefono;
    private String correo;

    private short idEstado;
    private String descEstado;
    private Date fechaRegistro;




}
