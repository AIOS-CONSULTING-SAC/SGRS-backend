package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.security.JwtService;
import com.aios.sgrs.security.TokenType;
import com.aios.sgrs.service.UsuarioService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
    //private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final HttpServletRequest httpServletRequest;
    public UsuarioController(UsuarioService usuarioService,
                             //                       AuthenticationManager authManager,
                             JwtService jwtService,
                             HttpServletRequest httpServletRequest) {
        this.usuarioService = usuarioService;
        //  this.authManager = authManager;
        this.jwtService = jwtService;
        this.httpServletRequest = httpServletRequest;
    }

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse login(@RequestBody UsuarioRequest req, HttpServletRequest httpServletRequest ) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UsuarioLogeadoResponse usuarioLogeadoResponse = usuarioService.iniciarSesion(req, httpServletRequest);
        if (usuarioLogeadoResponse.getResultado() != 3) {
            return ApiResponse.error(usuarioLogeadoResponse.getMensaje());
        }

       if (!encoder.matches(req.getPassword(), usuarioLogeadoResponse.getPassword())) {
           return ApiResponse.error("La contraseña es incorrecta");
       }

        Map<String,Object> claims = new HashMap<>();
        claims.put("codigoRol", usuarioLogeadoResponse.getRolId());
        claims.put("perfil", usuarioLogeadoResponse.getPerfil());
        claims.put("codigoEmpresa", usuarioLogeadoResponse.getEmpresaId());
        claims.put("codigoUsuario", usuarioLogeadoResponse.getUsuarioId());
        claims.put("tipoUsuario", usuarioLogeadoResponse.getTipoUsuarioId());
        claims.put("nombres", usuarioLogeadoResponse.getNombres());
        claims.put("apellidoP", usuarioLogeadoResponse.getApellidoP());
        claims.put("apellidoM", usuarioLogeadoResponse.getApellidoM());
        claims.put("cliente", usuarioLogeadoResponse.getCliente());
        claims.put("descCliente", usuarioLogeadoResponse.getDescCliente());
        String accessToken = jwtService.generateToken(
                claims,
                req.getUsuario(),
                TokenType.ACCESS
        );

        String refreshToken = jwtService.generateToken(
                Collections.emptyMap(),
                req.getUsuario(),
                TokenType.REFRESH
        );

        Map<String,String> payload = Map.of(
                "accessToken",  accessToken,
                "refreshToken", refreshToken
        );
        return ApiResponse.mensaje(200, usuarioLogeadoResponse.getMensaje(), payload);

    }

    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarUsuarioRequest request) {
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return usuarioService.guardarUsuario(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarUsuarioRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return usuarioService.guardarUsuario(request);
    }


    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarUsuarios(@RequestParam(value = "tipoUser",required = false) Integer tipoUser,
                               @RequestParam(value = "perfil",required = false) Integer perfil,
                               @RequestParam(value = "cliente",required = false) Integer cliente,
                               @RequestParam(value = "nroDocumento",required = false) String nroDocumento,
                               @RequestParam(value = "nombre",required = false) String nombre,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return usuarioService.listado(tipoUser, perfil, cliente, nroDocumento, nombre, idEstado);
    }


    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idUsuario") Integer idUsuario){
        Integer codigoUsuario = getCodigoUsuario();
        return usuarioService.eliminarUsuario(idUsuario, codigoUsuario);
    }

}
