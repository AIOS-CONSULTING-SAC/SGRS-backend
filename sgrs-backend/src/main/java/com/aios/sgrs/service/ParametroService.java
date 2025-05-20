package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.request.parametro.GuardarParametroRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface ParametroService {
    ApiResponse listado(Integer codModulo, Integer codOpcion, Integer codPrefijo, String desc1, String desc2, String desc3, Integer int01, Integer int02, Integer idEstado) throws ServiceException;
    ApiResponse guardarParametro(GuardarParametroRequest request) throws ServiceException;
    ApiResponse eliminarParametro(Integer idParametro, Integer usuarioSesion) throws ServiceException;
}
