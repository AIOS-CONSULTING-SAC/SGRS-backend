package com.aios.sgrs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;

public class JwtUtils {

    // Decodifica el payload del JWT y lo convierte en un Map
    public static Map<String, Object> decodePayload(String jwt) throws Exception {
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Token JWT inválido");
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(payloadJson, Map.class);
    }

    // Extrae el username del JWT
    public static Integer getCodigoUsuario(String jwt) {
        try {
            Map<String, Object> payload = decodePayload(jwt);
            return (Integer) payload.get("codigoUsuario");
        } catch (Exception e) {
            return null;
        }
    }

}

