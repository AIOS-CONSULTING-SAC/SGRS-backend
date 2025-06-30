package com.aios.sgrs.controller;

import com.aios.sgrs.config.JwtUtils;
import com.aios.sgrs.model.request.empresa.GuardarEmpresaRequest;
import com.aios.sgrs.service.EmpresaService;
import com.aios.sgrs.utils.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final HttpServletRequest httpServletRequest;

    public EmpresaController(EmpresaService empresaService, HttpServletRequest httpServletRequest){
        this.empresaService = empresaService;
        this.httpServletRequest = httpServletRequest;
    }

    private Integer getCodigoUsuario() {
        return JwtUtils.getCodigoUsuario(httpServletRequest.getHeader("Authorization"));
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse listarEmpresas(@RequestParam(value = "razonSocial",required = false) String razonSocial,
                               @RequestParam(value = "ruc",required = false) String ruc,
                               @RequestParam(value = "idEstado",required = false) Integer idEstado){
        return empresaService.listado(razonSocial, ruc, idEstado);
    }

    @PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse guardar(@Valid @RequestBody GuardarEmpresaRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return empresaService.guardarEmpresa(request);
    }

    @PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse actualizar(@Valid @RequestBody GuardarEmpresaRequest request){
        Integer codigoUsuario = getCodigoUsuario();
        request.setUsuarioSesion(codigoUsuario);
        return empresaService.guardarEmpresa(request);
    }

    @DeleteMapping(value = "/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse eliminar(@RequestParam("idEmpresa") Integer idEmpresa){
        Integer codigoUsuario = getCodigoUsuario();
        return empresaService.eliminarEmpresa(idEmpresa, codigoUsuario);
    }

}
