package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioService {
    ApiResponse guardarUsuario(GuardarUsuarioRequest request) throws ServiceException;
    UsuarioLogeadoResponse iniciarSesion(UsuarioRequest usuarioRequest, HttpServletRequest request);

    ApiResponse listado(Integer codUsuario, Integer codEmpresa, Integer codCliente, Integer idEstado) throws ServiceException;

    ApiResponse eliminarUsuario(Integer idCliente, Integer usuarioSesion) throws ServiceException;
}
