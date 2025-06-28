package com.aios.sgrs.model.response.Dashboard;

import com.aios.sgrs.model.request.manejoResiduo.DetalleResiduoSolido;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardResponse {
    private Integer codResiduo;
    private String descResiduo;
    private Integer codUnidad;
    private String descUnidad;
    private Double totalAnio;
    private List<DetalleDashboard> detalleDashboard;
}
