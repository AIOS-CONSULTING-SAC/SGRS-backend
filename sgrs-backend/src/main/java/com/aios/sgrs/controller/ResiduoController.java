package com.aios.sgrs.controller;

import com.aios.sgrs.model.request.residuo.GuardarResiduoRequest;
import com.aios.sgrs.service.ResiduoService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/residuos")
public class ResiduoController {

    private final ResiduoService residuoService;

    public ResiduoController(ResiduoService residuoService ){
        this.residuoService = residuoService;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarResiduos(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return residuoService.listado(codCliente, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idResiduo") Integer idResiduo,@RequestParam("usuarioSesion") String usuarioSesion){
        return residuoService.eliminarResiduo(idResiduo, usuarioSesion);
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
