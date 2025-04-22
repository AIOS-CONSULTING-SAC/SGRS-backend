package com.aios.common.response;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

@JsonPropertyOrder({"headers", "codigo", "mensaje", "totalPaginas", "totalRegistros", "respuesta", "otrosParams"})
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Response<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final HttpHeaders headers;
    private final int codigo;
    private final String mensaje;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private final int totalRegistros;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private final int totalPaginas;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T respuesta;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Object> otrosParams;

    private Response(ApiResponseBuilder<T> builder) {
        this.headers = builder.headers;
        this.codigo = builder.codigo;
        this.mensaje = builder.mensaje;
        this.totalPaginas = builder.totalPaginas;
        this.totalRegistros = builder.totalRegistros;
        this.respuesta = builder.respuesta;
        this.otrosParams = builder.otrosParams;
    }

    @JsonCreator
    public Response(@JsonProperty("headers") @JsonInclude(JsonInclude.Include.NON_NULL) HttpHeaders headers,
                    @JsonProperty("codigo") int codigo,
                    @JsonProperty("mensaje") String mensaje,
                    @JsonProperty("totalRegistros") @JsonInclude(JsonInclude.Include.NON_DEFAULT) int totalRegistros,
                    @JsonProperty("totalPaginas") @JsonInclude(JsonInclude.Include.NON_DEFAULT) int totalPaginas,
                    @JsonProperty("respuesta") @JsonInclude(JsonInclude.Include.NON_NULL) T respuesta,
                    @JsonProperty("otrosParams") @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, Object> otrosParams) {
        this.headers = headers;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.totalRegistros = totalRegistros;
        this.totalPaginas = totalPaginas;
        this.respuesta = respuesta;
        this.otrosParams = otrosParams;
    }

    public static class ApiResponseBuilder<T> {

        private HttpHeaders headers = new HttpHeaders();
        private final int codigo;
        private final String mensaje;
        private int totalRegistros;
        private int totalPaginas;
        private T respuesta;
        private Map<String, Object> otrosParams = Collections.emptyMap();


        public ApiResponseBuilder(int httpStatusCode, String message) {
            this.codigo = httpStatusCode;
            this.mensaje = message;
        }


        public ApiResponseBuilder<T> withHttpHeader(HttpHeaders httpHeader) {
            this.headers = httpHeader;
            return this;
        }

        public ApiResponseBuilder<T> withData(T respuesta) {
            this.respuesta = respuesta;
            return this;
        }

        public ApiResponseBuilder<T> withCount(int totalPaginas, int totalRegistros) {
            this.totalPaginas = totalPaginas;
            this.totalRegistros = totalRegistros;
            return this;
        }

        public ApiResponseBuilder<T> withOtherParams(Map<String, Object> otherParams) {
            this.otrosParams = otherParams;
            return this;
        }

        public ResponseEntity<Response<T>> build() {
            Response<T> response = new Response<>(this);
            return ResponseEntity.status(response.getCodigo()).headers(response.getHeaders())
                    .body(response);
        }
    }

    public boolean hayError() {
        return codigo < 200 || codigo > 300;
    }
}