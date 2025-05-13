package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.LocalDao;
import com.aios.sgrs.dao.ResiduoDao;
import com.aios.sgrs.model.request.local.EliminarLocalRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.model.request.residuo.EliminarResiduoRequest;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.model.response.local.LocalResponse;
import com.aios.sgrs.model.response.local.ResiduoResponse;
import com.aios.sgrs.service.LocalService;
import com.aios.sgrs.service.ResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResiduoServiceImpl implements ResiduoService {

    private final ResiduoDao residuoDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public ResiduoServiceImpl(ResiduoDao residuoDao, ApiResponseBuilder<Object>  responseBuilder){
        this.residuoDao = residuoDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listado(Integer codCliente, Integer idEstado) throws ServiceException {
        List<ResiduoResponse> listado = residuoDao.listado(1,codCliente, idEstado);


        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }


    @Override
    public ApiResponse guardarResiduo(GuardarResiduoRequest request) throws ServiceException {
        if(request.getResiduo()==null){
            request.setIdEstado((short) 1);
        }
        residuoDao.guardarResiduo(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }


    @Override
    public ApiResponse eliminarResiduo(Integer idResiduo, String usuarioSesion) throws ServiceException {

        EliminarResiduoRequest request = new EliminarResiduoRequest();
        request.setResiduo(idResiduo);
        request.setUsuarioSesion(usuarioSesion);

        residuoDao.eliminarResiduo(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
