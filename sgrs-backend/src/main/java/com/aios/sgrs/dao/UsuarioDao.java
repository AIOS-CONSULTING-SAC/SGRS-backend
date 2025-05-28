package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.usuario.EliminarUsuarioRequest;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.model.response.usuario.UsuarioResponse;

import java.util.List;

public interface UsuarioDao {
    boolean guardarUsuario(GuardarUsuarioRequest request) throws AccesoDaoException;
    UsuarioLogeadoResponse iniciarSesion(String usuario, String password) throws AccesoDaoException;

    List<UsuarioResponse> listado(int tipoSQL, Integer tipoUser, Integer perfil, String nroDocumento, String nombre, Integer idEstado) throws AccesoDaoException;

    boolean eliminarUsuario(EliminarUsuarioRequest request) throws AccesoDaoException;
}
