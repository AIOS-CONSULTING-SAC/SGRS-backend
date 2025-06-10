package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;

import java.util.List;

public interface ManejoResiduoDao {
    List<ManejoResiduoResponse> listarManejoResiduos(Integer codCliente, Integer anio, String locales, Integer estado) throws AccesoDaoException;

    boolean guardarManejoResiduo(GuardarManejoResiduoRequest request) throws AccesoDaoException;

}
