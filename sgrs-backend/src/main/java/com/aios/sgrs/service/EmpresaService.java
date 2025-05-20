package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface EmpresaService {
    ApiResponse listado(String razonSocial, String ruc, Integer idEstado) throws ServiceException;
    ApiResponse guardarEmpresa(GuardarEmpresaRequest request) throws ServiceException;
    ApiResponse eliminarEmpresa(Integer idEmpresa, Integer usuarioSesion) throws ServiceException;
}
