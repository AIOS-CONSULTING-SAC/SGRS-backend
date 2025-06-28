package com.aios.sgrs.model.response.Dashboard;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetalleDashboard {
    private Integer codLocal;
    private String descLocal;
    private Double total;
}
