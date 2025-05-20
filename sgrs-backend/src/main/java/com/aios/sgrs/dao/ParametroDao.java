package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.cliente.EliminarClienteRequest;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.model.request.parametro.EliminarParametroRequest;
import com.aios.sgrs.model.request.parametro.GuardarParametroRequest;
import com.aios.sgrs.model.response.cliente.ClienteResponse;
import com.aios.sgrs.model.response.parametro.ParametroResponse;

import java.util.List;

public interface ParametroDao {
    List<ParametroResponse> listarParametros(Integer tipoSQL, Integer codModulo, Integer codOpcion, Integer codPrefijo, String desc1, String desc2, String desc3, Integer int01, Integer int02, Integer estado) throws AccesoDaoException;
    boolean guardarParametro(GuardarParametroRequest request) throws AccesoDaoException;

    boolean desactivarParametro(EliminarParametroRequest request) throws AccesoDaoException;

//    boolean desactivarCliente(EliminarClienteRequest request) throws AccesoDaoException;
}
