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
    private Integer codCliente;
    private Integer codTipoUser;
    private Integer codPerfil;
    private Integer codTipoDoc;
    private String ndoc;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String correo;

    private short idEstado;
    private String descEstado;
    private Date fechaRegistro;

}
