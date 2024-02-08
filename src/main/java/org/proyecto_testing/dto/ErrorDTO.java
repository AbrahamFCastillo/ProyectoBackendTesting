package org.proyecto_testing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String codigo;
    private String mensaje;
    private Object detalles;
}