package com.aios.sgrs.controller;

import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import com.aios.sgrs.model.request.seguridad.UsuarioRequest;
import com.aios.sgrs.model.request.usuario.GuardarUsuarioRequest;
import com.aios.sgrs.security.JwtService;
import com.aios.sgrs.security.TokenType;
import com.aios.sgrs.service.UsuarioService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public UsuarioController(UsuarioService usuarioService,
                             //                       AuthenticationManager authManager,
                             JwtService jwtService) {
        this.usuarioService = usuarioService;
      //  this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse login(@RequestBody UsuarioRequest req, HttpServletRequest httpServletRequest ) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UsuarioLogeadoResponse usuarioLogeadoResponse = usuarioService.iniciarSesion(req, httpServletRequest);

        if (usuarioLogeadoResponse.getResultado() != 3) {
            return ApiResponse.error(usuarioLogeadoResponse.getMensaje());
        }

        System.out.printf("encoder_KVN: "+ encoder.matches(req.getPassword(), usuarioLogeadoResponse.getPassword()));

        if (!encoder.matches(req.getPassword(), usuarioLogeadoResponse.getPassword())) {
            return ApiResponse.error("La contraseña es incorrecta");
        }

        //if (!encoder.matches(req.getPassword(), usuarioLogeadoResponse.getPassword())) {
        //    return ApiResponse.error("La contraseña es incorrecta");
        //}

        Map<String,Object> claims = new HashMap<>();
        claims.put("codigoRol", usuarioLogeadoResponse.getRolId());
        claims.put("codigoEmpresa", usuarioLogeadoResponse.getEmpresaId());
        claims.put("codigoUsuario", usuarioLogeadoResponse.getUsuarioId());
        claims.put("tipoUsuario", usuarioLogeadoResponse.getTipoUsuarioId());
        claims.put("nombres", usuarioLogeadoResponse.getNombres());
        claims.put("apellidoP", usuarioLogeadoResponse.getApellidoP());
        claims.put("apellidoM", usuarioLogeadoResponse.getApellidoM());
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
        return usuarioService.guardarUsuario(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarUsuarioRequest request){
        return usuarioService.guardarUsuario(request);
    }


    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarUsuarios(@RequestParam(value = "codUsuario",required = false) Integer codUsuario,
                               @RequestParam(value = "codEmpresa",required = false) Integer codEmpresa,
                               @RequestParam(value = "codCliente",required = false) Integer codCliente,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return usuarioService.listado(codUsuario, codEmpresa, codCliente, idEstado);
    }


    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idUsuario") Integer idUsuario,@RequestParam("usuarioSesion") Integer usuarioSesion){
        return usuarioService.eliminarUsuario(idUsuario, usuarioSesion);
    }

}
