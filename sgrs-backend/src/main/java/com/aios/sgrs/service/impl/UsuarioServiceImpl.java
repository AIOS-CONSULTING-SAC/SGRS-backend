package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.service.EmailService;
import com.aios.sgrs.service.UsuarioService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;
    private final EmailService emailService;
    private final ApiResponseBuilder<Object> responseBuilder;
    public UsuarioServiceImpl(UsuarioDao usuarioDao, EmailService emailService, ApiResponseBuilder<Object>  responseBuilder){
        this.usuarioDao = usuarioDao;
        this.emailService = emailService;
        this.responseBuilder = responseBuilder;
    }

//    @Override
//    public ApiResponse guardarUsuario(GuardarUsuarioRequest request) throws ServiceException {
//        usuarioDao.guardarUsuario(request);
//        String codRpuesta = "501";
//        if(codRpuesta.equals("501")){
//            try {
////                emailService.enviarCorreoUsuario( request.getCorreo(), request.getUsuarioSesion(), "123");
//                emailService.enviarCorreoUsuario( request.getCorreo(), "USUARIO PRUEBA", "123");
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            return ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
//        }
//        return  ApiResponse.error(codRpuesta);
//    }

    @Override
    public ApiResponse guardarUsuario(GuardarUsuarioRequest request) throws ServiceException {
        if(request.getIdUsuario()==null){
            request.setIdEstado((short) 1);
        }
        usuarioDao.guardarUsuario(request);
        String codRpuesta = request.getMensaje();
        if(codRpuesta.equals("200")) {
//            try {
//                emailService.enviarCorreoUsuario( request.getCorreo(), "USUARIO PRUEBA", "123");
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            return ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
        }

        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }


    @Override
    public UsuarioLogeadoResponse iniciarSesion(UsuarioRequest usuarioRequest, HttpServletRequest request) {
        return usuarioDao.iniciarSesion(usuarioRequest.getUsuario(), usuarioRequest.getPassword());
    }
}
