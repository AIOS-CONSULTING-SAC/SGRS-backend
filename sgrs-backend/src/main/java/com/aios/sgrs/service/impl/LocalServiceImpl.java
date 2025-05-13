package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.LocalDao;
import com.aios.sgrs.model.request.local.EliminarLocalRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.response.local.LocalResponse;
import com.aios.sgrs.service.LocalService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocalServiceImpl implements LocalService {

    private final LocalDao localDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public LocalServiceImpl(LocalDao localDao, ApiResponseBuilder<Object>  responseBuilder){
        this.localDao = localDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listado(Integer codCliente, Integer idEstado) throws ServiceException {
        List<LocalResponse> listado = localDao.listado(1,codCliente, idEstado);


        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }


    @Override
    public ApiResponse guardarLocal(GuardarLocalRequest request) throws ServiceException {
        if(request.getLocal()==null){
            request.setIdEstado((short) 1);
        }
        localDao.guardarLocal(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }


    @Override
    public ApiResponse eliminarLocal(Integer idLocal, String usuarioSesion) throws ServiceException {

        EliminarLocalRequest request = new EliminarLocalRequest();
        request.setLocal(idLocal);
        request.setUsuarioSesion(usuarioSesion);

        localDao.eliminarLocal(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
