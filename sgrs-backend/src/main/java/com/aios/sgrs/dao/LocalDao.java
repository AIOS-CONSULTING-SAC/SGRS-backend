package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.empresa.EliminarEmpresaRequest;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.request.local.EliminarLocalRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.response.empresa.EmpresaResponse;
import com.aios.sgrs.model.response.local.LocalResponse;

import java.util.List;

public interface LocalDao {
    List<LocalResponse> listado(int tipoSQL, Integer codCliente, String descLocal, Integer idEstado) throws AccesoDaoException;

    boolean guardarLocal(GuardarLocalRequest request) throws AccesoDaoException;

    boolean eliminarLocal(EliminarLocalRequest request) throws AccesoDaoException;

}
