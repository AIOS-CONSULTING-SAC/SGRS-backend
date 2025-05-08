package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.EmpresaDao;
import com.aios.sgrs.model.request.empresa.EliminarEmpresaRequest;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.response.empresa.EmpresaResponse;
import com.aios.sgrs.service.EmpresaService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaDao empresaDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public EmpresaServiceImpl(EmpresaDao empresaDao, ApiResponseBuilder<Object>  responseBuilder){
        this.empresaDao = empresaDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listado(String razonSocial, String ruc, Integer idEstado) throws ServiceException {
        List<EmpresaResponse> listado = empresaDao.listado(1,razonSocial, ruc, idEstado);
        System.out.printf("listado_xd: ", listado);
        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }

    @Override
    public ApiResponse guardarEmpresa(GuardarEmpresaRequest request) throws ServiceException {
        if(request.getEmpresa()==null){
            request.setIdEstado((short) 1);
        }
        empresaDao.guardarEmpresa(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }



    @Override
    public ApiResponse eliminarEmpresa(Integer idEmpresa, String usuarioSesion) throws ServiceException {

        EliminarEmpresaRequest request = new EliminarEmpresaRequest();
        request.setEmpresa(idEmpresa);
        request.setUsuarioSesion(usuarioSesion);

        empresaDao.eliminarEmpresa(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
