package com.aios.sgrs.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.aios.common.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int codigo;
    private String mensaje;

    private Object respuesta;

    public ApiResponse() {
       this.setCodigo(0);
    }

    public static ApiResponse error() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMensaje("ERROR");
       return apiResponse;
    }

    public static ApiResponse error(String mensaje) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCodigo(-1);
        apiResponse.setMensaje(mensaje);
        return apiResponse;
    }

    public static ApiResponse mensaje(int codigo,Object respuesta) {
        ApiResponse response = new ApiResponse();
        response.setCodigo(codigo);
        response.setRespuesta(respuesta);
        return response;
    }
    public static ApiResponse mensaje(Response respuesta) {
        ApiResponse response = new ApiResponse();
        response.setCodigo(response.getCodigo());
        response.setRespuesta(respuesta);
        return response;
    }

    public static ApiResponse mensaje(int codigo,String mensaje, Object respuesta) {
        ApiResponse response = new ApiResponse();
        response.setCodigo(codigo);
        response.setMensaje(mensaje);
        response.setRespuesta(respuesta);
        return response;
    }

    public static ApiResponse exito(String mensaje,Object respuesta) {
        ApiResponse response = new ApiResponse();
        response.setMensaje(mensaje);
        response.setRespuesta(respuesta);
        return response;
    }

    public static ApiResponse exito(Response response){
        return ApiResponse.exito(response.getMensaje(),response.getRespuesta());
    }

    public static ApiResponse parametrosIncorrectos() {
        ApiResponse response = new ApiResponse();
        response.setCodigo(-1);
        response.setMensaje("PARAMETROS_INCORRECTOS");
        return  response;
    }

    public static ApiResponse noExisteUsuario() {
        ApiResponse response = new ApiResponse();
        response.setCodigo(-1);
        response.setMensaje("USUARIO_NO_EXISTE");
        return  response;
    }

    public static ApiResponse noHayResultados(String mensaje) {
        ApiResponse response = new ApiResponse();
        response.setCodigo(-1);
        response.setMensaje(mensaje==null ? "No existen resultados" : mensaje);
        return  response;
    }

}
