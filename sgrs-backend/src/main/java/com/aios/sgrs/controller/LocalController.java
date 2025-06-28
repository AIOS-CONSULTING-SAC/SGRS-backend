package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.service.LocalService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/locales")
public class LocalController {

    private final LocalService localService;
    private final HttpServletRequest httpServletRequest;

    public LocalController(LocalService localService, HttpServletRequest httpServletRequest){
        this.localService = localService;
        this.httpServletRequest = httpServletRequest;
    }

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarLocales(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                              @RequestParam(value = "descLocal",required = false) String descLocal,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return localService.listado(codCliente, descLocal, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idLocal") Integer idLocal){
        Integer codigoUsuario = getCodigoUsuario();
        return localService.eliminarLocal(idLocal, codigoUsuario);
    }

    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarLocalRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return localService.guardarLocal(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarLocalRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return localService.guardarLocal(request);
    }

}
