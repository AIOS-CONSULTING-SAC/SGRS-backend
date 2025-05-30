package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.ParametroDao;
import com.aios.sgrs.model.request.parametro.EliminarParametroRequest;
import com.aios.sgrs.model.request.parametro.GuardarParametroRequest;
import com.aios.sgrs.model.response.parametro.ParametroResponse;
import com.aios.sgrs.service.ParametroService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParametroServiceImpl implements ParametroService {

    private final ParametroDao parametroDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public ParametroServiceImpl(ParametroDao parametroDao, ApiResponseBuilder<Object>  responseBuilder){
        this.parametroDao = parametroDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listado(Integer tipoSQL, Integer codModulo, Integer codOpcion, Integer codPrefijo, String desc1, String desc2, String desc3, Integer int01, Integer int02, Integer idEstado) throws ServiceException {
        List<ParametroResponse> listado = parametroDao.listarParametros(tipoSQL, codModulo, codOpcion, codPrefijo, desc1, desc2, desc3, int01, int02, idEstado);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }


    @Override
    public ApiResponse guardarParametro(GuardarParametroRequest request) throws ServiceException {
        if(request.getParametro()==null){
            request.setIdEstado((short) 1);
        }
        parametroDao.guardarParametro(request);
        String codRpuesta = request.getMensaje();

        System.out.println("codRpuesta_xd" + codRpuesta);
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }


    @Override
    public ApiResponse eliminarParametro(Integer idParametro, Integer usuarioSesion) throws ServiceException {

        EliminarParametroRequest request = new EliminarParametroRequest();
        request.setParametro(idParametro);
        request.setUsuarioSesion(usuarioSesion);

        parametroDao.desactivarParametro(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
