package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.utils.ApiResponse;

public interface ResiduoService {
    ApiResponse listado(Integer codCliente, Integer idEstado) throws ServiceException;

    ApiResponse guardarResiduo(GuardarResiduoRequest request) throws ServiceException;

    ApiResponse eliminarResiduo(Integer idResiduo, String usuarioSesion) throws ServiceException;

}
