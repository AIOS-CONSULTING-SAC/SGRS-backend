package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.service.ResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarResiduos(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                               @RequestParam(value = "descResiduo",required = false) String descResiduo,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return residuoService.listado(codCliente, descResiduo, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idResiduo") Integer idResiduo){
        Integer codigoUsuario = getCodigoUsuario();
        return residuoService.eliminarResiduo(idResiduo, codigoUsuario);
    }


    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarResiduoRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return residuoService.guardarResiduo(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarResiduoRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return residuoService.guardarResiduo(request);
    }

}
