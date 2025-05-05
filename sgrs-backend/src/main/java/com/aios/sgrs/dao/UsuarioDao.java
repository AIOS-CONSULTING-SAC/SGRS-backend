package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;

public interface UsuarioDao {
    boolean guardarUsuario(GuardarUsuarioRequest request) throws AccesoDaoException;
    UsuarioLogeadoResponse iniciarSesion(String usuario, String password) throws AccesoDaoException;
}
