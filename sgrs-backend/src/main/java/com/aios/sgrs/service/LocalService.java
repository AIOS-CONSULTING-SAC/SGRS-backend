package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface LocalService {
    ApiResponse listado(Integer codCliente, Integer idEstado) throws ServiceException;

    ApiResponse guardarLocal(GuardarLocalRequest request) throws ServiceException;

    ApiResponse eliminarLocal(Integer idLocal, Integer usuarioSesion) throws ServiceException;
}
