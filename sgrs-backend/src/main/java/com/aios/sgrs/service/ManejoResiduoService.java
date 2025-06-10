package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface ManejoResiduoService {

    ApiResponse listarManejoResiduos(Integer codCliente, Integer anio, String locales, Integer idEstado) throws ServiceException;

    ApiResponse guardarManejoResiduo(GuardarManejoResiduoRequest request) throws ServiceException;
}
