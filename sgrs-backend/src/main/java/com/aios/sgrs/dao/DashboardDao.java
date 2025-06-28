package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.model.response.Dashboard.DashboardResponse;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;

import java.util.List;

public interface DashboardDao {
    List<DashboardResponse> listarDashboard01(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws AccesoDaoException;

    List<DashboardResponse> listarDashboard02(Integer codCliente, Integer anio, String locales, String meses, String residuos) throws AccesoDaoException;

}
