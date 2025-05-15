package com.aios.sgrs.dao;

import com.aios.common.exception.AccesoDaoException;
import com.aios.sgrs.model.request.residuo.EliminarResiduoRequest;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.model.response.residuo.ResiduoResponse;

import java.util.List;

public interface ResiduoDao {
    List<ResiduoResponse> listado(int tipoSQL, int codCliente, Integer idEstado) throws AccesoDaoException;

    boolean guardarResiduo(GuardarResiduoRequest request) throws AccesoDaoException;

    boolean eliminarResiduo(EliminarResiduoRequest request) throws AccesoDaoException;

}
