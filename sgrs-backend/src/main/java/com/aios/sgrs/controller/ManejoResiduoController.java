package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.manejoResiduo.GuardarManejoResiduoRequest;
import com.aios.sgrs.service.ManejoResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/manejoresiduos")
public class ManejoResiduoController {

    private final ManejoResiduoService manejoResiduoService;
    private final HttpServletRequest httpServletRequest;

    public ManejoResiduoController(ManejoResiduoService manejoResiduoService, HttpServletRequest httpServletRequest){
        this.manejoResiduoService = manejoResiduoService;
        this.httpServletRequest = httpServletRequest;
    }

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listar(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                              @RequestParam(value = "anio",required = false) Integer anio,
                              @RequestParam(value = "locales",required = false) String locales,
                              @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return manejoResiduoService.listarManejoResiduos(codCliente, anio, locales, idEstado);
    }


    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarManejoResiduoRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return manejoResiduoService.guardarManejoResiduo(request);
    }

}
