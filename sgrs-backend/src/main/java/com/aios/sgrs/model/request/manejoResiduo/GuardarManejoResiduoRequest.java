package com.aios.sgrs.model.request.manejoResiduo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GuardarManejoResiduoRequest {
    private Integer codLocal;
    private Integer codResiduo;
    private Integer anio;
    private List<DetalleResiduoSolido> detalle;

    private Integer idEstado;
    private Integer usuarioSesion;
    private String mensaje;
}
