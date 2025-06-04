package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.ClienteDao;
import com.aios.sgrs.model.request.cliente.EliminarClienteRequest;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.model.response.cliente.ClienteResponse;
import com.aios.sgrs.service.ClienteService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDao clienteDao;
    private final ApiResponseBuilder<Object> responseBuilder;

    public ClienteServiceImpl(ClienteDao clienteDao, ApiResponseBuilder<Object>  responseBuilder){
        this.clienteDao = clienteDao;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ApiResponse listado(int codEmpresa, String razonSocial, String ruc, Integer idEstado) throws ServiceException {
        List<ClienteResponse> listado = clienteDao.listarClientes(1,codEmpresa,razonSocial, ruc, idEstado);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }

    @Override
    public ApiResponse guardarCliente(GuardarClienteRequest request) throws ServiceException {
        if(request.getCliente()==null){
            request.setIdEstado( 1);
        }
        clienteDao.guardarCliente(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }



    @Override
    public ApiResponse eliminarCliente(Integer idCliente,Integer idEmpresa, Integer usuarioSesion) throws ServiceException {

        EliminarClienteRequest request = new EliminarClienteRequest();
        request.setCliente(idCliente);
        request.setCodEmpresa(idEmpresa);
        request.setUsuarioSesion(usuarioSesion);

        clienteDao.desactivarCliente(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }
}
