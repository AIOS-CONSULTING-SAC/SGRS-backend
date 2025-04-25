package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.empresa.EliminarEmpresaRequest;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.response.empresa.EmpresaResponse;

import java.util.List;

public interface EmpresaDao {
    List<EmpresaResponse> listado(int tipoSQL, String razonSocial, String ruc, Integer idEstado) throws AccesoDaoException;
    boolean guardarEmpresa(GuardarEmpresaRequest request) throws AccesoDaoException;
    boolean eliminarEmpresa(EliminarEmpresaRequest request) throws AccesoDaoException;
}
