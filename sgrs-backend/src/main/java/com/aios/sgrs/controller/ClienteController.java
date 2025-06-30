package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.cliente.GuardarClienteRequest;
import com.aios.sgrs.service.ClienteService;
import com.aios.sgrs.utils.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final HttpServletRequest httpServletRequest;

    public ClienteController(ClienteService clienteService, HttpServletRequest httpServletRequest){
        this.clienteService = clienteService;
        this.httpServletRequest = httpServletRequest;
    }

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
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
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return clienteService.guardarCliente(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarClienteRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return clienteService.guardarCliente(request);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idCliente") Integer idCliente,
                         @RequestParam("idEmpresa") Integer idEmpresa){
        Integer codigoUsuario = getCodigoUsuario();
        return clienteService.eliminarCliente(idCliente, idEmpresa, codigoUsuario);
    }

}
