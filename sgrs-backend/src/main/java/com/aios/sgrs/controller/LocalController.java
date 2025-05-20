package com.aios.sgrs.controller;

import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.model.request.local.GuardarLocalRequest;
import com.aios.sgrs.service.EmpresaService;
import com.aios.sgrs.service.LocalService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/locales")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService ){
        this.localService = localService;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarLocales(@RequestParam(value = "codCliente",required = false) Integer codCliente,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return localService.listado(codCliente, idEstado);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idLocal") Integer idLocal,@RequestParam("usuarioSesion") Integer usuarioSesion){
        return localService.eliminarLocal(idLocal, usuarioSesion);
    }


    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarLocalRequest request){
        return localService.guardarLocal(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarLocalRequest request){
        return localService.guardarLocal(request);
    }

}
