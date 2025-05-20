package com.aios.sgrs.controller;

import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.service.ClienteService;
import com.aios.sgrs.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarEmpresas( @RequestParam(value = "idEmpresa",required = false) Integer idEmpresa,
                                @RequestParam(value = "razonSocial",required = false) String razonSocial,
                               @RequestParam(value = "ruc",required = false) String ruc,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return clienteService.listado(idEmpresa,razonSocial, ruc, idEstado);
    }

    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarClienteRequest request){
        return clienteService.guardarCliente(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarClienteRequest request){
        return clienteService.guardarCliente(request);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idCliente") Integer idCliente,
                         @RequestParam("idEmpresa") Integer idEmpresa,
                         @RequestParam("usuarioSesion") Integer usuarioSesion){
        return clienteService.eliminarCliente(idCliente, idEmpresa, usuarioSesion);
    }

}
