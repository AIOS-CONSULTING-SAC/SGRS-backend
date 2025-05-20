package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface ClienteService {
    ApiResponse listado(int codEmpresa, String razonSocial, String ruc, Integer idEstado) throws ServiceException;
    ApiResponse guardarCliente(GuardarClienteRequest request) throws ServiceException;
    ApiResponse eliminarCliente(Integer idCliente,Integer idEmpresa, Integer usuarioSesion) throws ServiceException;
}
