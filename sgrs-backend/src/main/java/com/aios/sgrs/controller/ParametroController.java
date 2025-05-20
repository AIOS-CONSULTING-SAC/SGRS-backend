package com.aios.sgrs.controller;

import com.aios.sgrs.model.request.parametro.GuardarParametroRequest;
import com.aios.sgrs.service.ParametroService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/parametros")
public class ParametroController {

    private final ParametroService parametroService;

    public ParametroController(ParametroService parametroService ){
        this.parametroService = parametroService;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarParametro(@RequestParam(value = "codModulo",required = false) Integer codModulo,
                                @RequestParam(value = "codOpcion",required = false) Integer codOpcion,
                                @RequestParam(value = "codPrefijo",required = false) Integer codPrefijo,
                                @RequestParam(value = "desc1",required = false) String desc1,
                                @RequestParam(value = "desc2",required = false) String desc2,
                                @RequestParam(value = "desc3",required = false) String desc3,
                                @RequestParam(value = "int01",required = false) Integer int01,
                                @RequestParam(value = "int02",required = false) Integer int02,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return parametroService.listado(codModulo, codOpcion, codPrefijo, desc1, desc2, desc3, int01, int02, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idParametro") Integer idParametro,@RequestParam("usuarioSesion") Integer usuarioSesion){
        return parametroService.eliminarParametro(idParametro, usuarioSesion);
    }


    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarParametroRequest request){
        return parametroService.guardarParametro(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarParametroRequest request){
        return parametroService.guardarParametro(request);
    }

}
