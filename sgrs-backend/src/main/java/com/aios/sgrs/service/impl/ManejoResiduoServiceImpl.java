package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.ManejoResiduoDao;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.model.response.manejoResiduo.ManejoResiduoResponse;
import com.aios.sgrs.service.ManejoResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManejoResiduoServiceImpl implements ManejoResiduoService {

    private final ManejoResiduoDao manejoResiduoDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public ManejoResiduoServiceImpl(ManejoResiduoDao manejoResiduoDao, ApiResponseBuilder<Object>  responseBuilder){
        this.manejoResiduoDao = manejoResiduoDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listarManejoResiduos(Integer codCliente, Integer anio, String locales, Integer idEstado) throws ServiceException {
        List<ManejoResiduoResponse> listado = manejoResiduoDao.listarManejoResiduos(codCliente, anio, locales, idEstado);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }


    @Override
    public ApiResponse guardarManejoResiduo(GuardarManejoResiduoRequest request) throws ServiceException {
        manejoResiduoDao.guardarManejoResiduo(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
