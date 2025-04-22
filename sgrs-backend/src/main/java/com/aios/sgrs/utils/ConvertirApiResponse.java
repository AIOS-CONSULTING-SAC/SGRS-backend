package com.aios.sgrs.utils;


import com.aios.common.response.Response;

public class ConvertirApiResponse {

    public static ApiResponse exito(Response response){
        return ApiResponse.exito(response.getMensaje(),response.getRespuesta());
    }
    public static ApiResponse mensaje(int codigo, String respuesta){
        return ApiResponse.mensaje(codigo,respuesta);
    }

    public static ApiResponse mensaje(Response response){
        return ApiResponse.mensaje(response.getCodigo(),response.getMensaje(), response.getRespuesta());
    }
}
