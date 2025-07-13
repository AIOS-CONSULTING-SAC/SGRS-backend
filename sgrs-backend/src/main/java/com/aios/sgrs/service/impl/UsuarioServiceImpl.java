package com.aios.sgrs.service.impl;

import com.aios.common.exception.ServiceException;
import com.aios.common.response.ApiResponseBuilder;
import com.aios.sgrs.Objeto;
import com.aios.sgrs.dao.ParametroDao;
import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.EliminarUsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.model.response.parametro.ParametroResponse;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.response.usuario.UsuarioResponse;
import com.aios.sgrs.service.EmailService;
import com.aios.sgrs.service.UsuarioService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;
    private final ParametroDao parametroDao;
    private final EmailService emailService;
    private final ApiResponseBuilder<Object> responseBuilder;
    public UsuarioServiceImpl(UsuarioDao usuarioDao, ParametroDao parametroDao, EmailService emailService, ApiResponseBuilder<Object>  responseBuilder){
        this.usuarioDao = usuarioDao;
        this.parametroDao = parametroDao;
        this.emailService = emailService;
        this.responseBuilder = responseBuilder;
    }


    @Override
    public ApiResponse guardarUsuario(GuardarUsuarioRequest request) throws ServiceException {
        if(request.getIdUsuario()==null){
            request.setIdEstado((short) 1);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordGenerada =request.getApellidoP().substring(0,2).concat(request.getNombre().substring(0,3)).concat(request.getApellidoM().substring(0,2));
        String passwordEncriptada = encoder.encode(passwordGenerada);
        request.setPassword(passwordEncriptada);
        usuarioDao.guardarUsuario(request);
        String codRpuesta = request.getMensaje();

        if(codRpuesta.equals("200") && request.getIdUsuario()==null) {
            try {
                List<ParametroResponse> listMail = parametroDao.listarParametros(1,1, 2, 4, null, null, null, null, null, 1);

                ParametroResponse resultadoMail = listMail.stream()
                        .filter(p -> p.getCorrelativo() == request.getIdEmpresa())
                        .findFirst()
                        .orElse(null);

                if (resultadoMail != null) {
                    String empresa = resultadoMail.getDescripcion1();
                    String link = resultadoMail.getDescripcion2();

                    List<ParametroResponse> listadoCredenciales = parametroDao.listarParametros(1,1, 2, 5, null, null, null, null, null, 1);

                    ParametroResponse resultadoCredenciales = listadoCredenciales.stream()
                            .filter(p -> p.getCorrelativo() == request.getIdEmpresa())
                            .findFirst()
                            .orElse(null);

                    if (resultadoCredenciales != null) {
                        String host = resultadoCredenciales.getDescripcion1();
                        String username = resultadoCredenciales.getDescripcion2();
                        String password = resultadoCredenciales.getDescripcion3();
                        Integer port = resultadoCredenciales.getEntero01();

                        emailService.enviarCorreoUsuario( request.getCorreo(), request.getNombre()+ " " + request.getApellidoP() + " " + request.getApellidoM(),
                                passwordGenerada,
                                empresa, link,
                               host, port, username, password);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
        }

        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }


    @Override
    public UsuarioLogeadoResponse iniciarSesion(UsuarioRequest usuarioRequest, HttpServletRequest request) {
        return usuarioDao.iniciarSesion(usuarioRequest.getUsuario(), usuarioRequest.getPassword());
    }


    @Override
    public ApiResponse listado(Integer tipoUser, Integer perfil, Integer cliente, String nroDocumento, String nombre, Integer idEstado) throws ServiceException {
        List<UsuarioResponse> listado = usuarioDao.listado(1,tipoUser, perfil, cliente, nroDocumento, nombre, idEstado);

        return Objeto.nonEmpty(listado)
                ? ApiResponse.exito(responseBuilder.respuestaConExito(listado).getBody())
                : ApiResponse.noHayResultados(null);
    }



    @Override
    public ApiResponse eliminarUsuario(Integer idCliente, Integer usuarioSesion) throws ServiceException {

        EliminarUsuarioRequest request = new EliminarUsuarioRequest();
        request.setUsuario(idCliente);
        request.setUsuarioSesion(usuarioSesion);

        usuarioDao.eliminarUsuario(request);
        String codRpuesta = request.getMensaje();
        return !codRpuesta.equals("200") ? ApiResponse.error(codRpuesta) : ApiResponse.exito(responseBuilder.respuestaConExito(codRpuesta).getBody());
    }




}
