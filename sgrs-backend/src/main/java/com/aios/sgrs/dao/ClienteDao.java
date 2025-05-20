package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.cliente.EliminarClienteRequest;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.model.response.cliente.ClienteResponse;

import java.util.List;

public interface ClienteDao {
    List<ClienteResponse> listarClientes(int tipoSQL,int codEmpresa, String razonSocial, String ruc, Integer estado) throws AccesoDaoException;
    boolean guardarCliente(GuardarClienteRequest request) throws AccesoDaoException;
    boolean desactivarCliente(EliminarClienteRequest request) throws AccesoDaoException;
}
