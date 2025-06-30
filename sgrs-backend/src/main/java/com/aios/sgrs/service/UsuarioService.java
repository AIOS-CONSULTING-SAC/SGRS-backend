package com.aios.sgrs.service;

import com.aios.common.exception.ServiceException;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.utils.ApiResponse;

import javax.servlet.http.HttpServletRequest;

public interface UsuarioService {
    ApiResponse guardarUsuario(GuardarUsuarioRequest request) throws ServiceException;
    UsuarioLogeadoResponse iniciarSesion(UsuarioRequest usuarioRequest, HttpServletRequest request);

    abstract ApiResponse listado(Integer tipoUser, Integer perfil, Integer cliente, String nroDocumento, String nombre, Integer idEstado) throws ServiceException;

    ApiResponse eliminarUsuario(Integer idCliente, Integer usuarioSesion) throws ServiceException;
}
