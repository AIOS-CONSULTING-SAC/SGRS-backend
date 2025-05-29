package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.service.ResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/residuos")
public class ResiduoController {

    private final ResiduoService residuoService;
    private final HttpServletRequest httpServletRequest;
    public ResiduoController(ResiduoService residuoService,
                             HttpServletRequest httpServletRequest){
        this.residuoService = residuoService;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarResiduos(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return residuoService.listado(codCliente, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idResiduo") Integer idResiduo,@RequestParam("usuarioSesion") Integer usuarioSesion){
        System.out.println(httpServletRequest.getHeader("Authorization"));
        Integer codigoUsuario = JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
        System.out.println(codigoUsuario);
        return residuoService.eliminarResiduo(idResiduo, codigoUsuario);
    }


    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarResiduoRequest request){
        return residuoService.guardarResiduo(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarResiduoRequest request){
        return residuoService.guardarResiduo(request);
    }

}
