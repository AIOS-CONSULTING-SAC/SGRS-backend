package com.aios.sgrs.controller;

import com.aios.sgrs.service.DashboardService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/dashboards")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping(value = "/listar01", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listar01(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                         @RequestParam(value = "anio",required = false) Integer anio,
                         @RequestParam(value = "locales",required = false) String locales,
                         @RequestParam(value = "meses",required = false) String meses,
                         @RequestParam(value = "residuos",required = false) String residuos){
        return dashboardService.listarDashboard01(codCliente, anio, locales, meses, residuos);
    }


    @GetMapping(value = "/listar02", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listar02(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                       @RequestParam(value = "anio",required = false) Integer anio,
                       @RequestParam(value = "locales",required = false) String locales,
                       @RequestParam(value = "meses",required = false) String meses,
                       @RequestParam(value = "residuos",required = false) String residuos){
        return dashboardService.listarDashboard02(codCliente, anio, locales, meses, residuos);
    }



}
