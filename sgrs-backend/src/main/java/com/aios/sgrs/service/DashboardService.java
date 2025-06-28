package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface DashboardService {

    ApiResponse listarDashboard01(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws ServiceException;

    ApiResponse listarDashboard02(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws ServiceException;
}
