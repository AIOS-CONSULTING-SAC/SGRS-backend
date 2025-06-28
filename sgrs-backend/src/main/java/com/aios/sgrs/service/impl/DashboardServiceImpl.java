package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.DashboardDao;
import com.aios.sgrs.dao.ManejoResiduoDao;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.model.response.Dashboard.DashboardResponse;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;
import com.aios.sgrs.service.DashboardService;
import com.aios.sgrs.service.ManejoResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final DashboardDao dashboardDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public DashboardServiceImpl(DashboardDao dashboardDao, ApiResponseBuilder<Object>  responseBuilder){
        this.dashboardDao = dashboardDao;
        this.responseBuilder = responseBuilder;
    }


    @Override
    public ApiResponse listarDashboard01(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws ServiceException {
        List<DashboardResponse> listado = dashboardDao.listarDashboard01(codCliente, anio, locales, meses, residuos);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }


    @Override
    public ApiResponse listarDashboard02(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws ServiceException {
        List<DashboardResponse> listado = dashboardDao.listarDashboard02(codCliente, anio, locales, meses, residuos);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }

}
