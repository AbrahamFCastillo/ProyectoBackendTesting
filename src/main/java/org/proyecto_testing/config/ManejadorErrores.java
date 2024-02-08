package org.proyecto_testing.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.proyecto_testing.dto.ErrorDTO;
import org.proyecto_testing.exception.ErrorTiempoEjecucion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ManejadorErrores {
    /*  Extrae datos sobre el error ocurrido
   * 
   */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO validationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(x -> x.getDefaultMessage()).toList();
        return ErrorDTO.builder()
            .codigo("ERR_VALID")
            .mensaje("Existen errrores en los datos de entrada")
            .detalles(errors)
            .build();
    }

    @ExceptionHandler(ErrorTiempoEjecucion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO runtimeError(ErrorTiempoEjecucion ex) {
        return ErrorDTO.builder()
            .codigo(ex.getCodigo())
            .mensaje(ex.getMessage())
            .detalles(ex.getDetalles())
            .build();
    }

    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO unknownError(Exception ex) {
        log.error(ex.getMessage());
        return ErrorDTO.builder()
            .codigo("ERR_UNKNOWN")
            .mensaje("Ocurrió un error inesperado")
            .build();
    }
}
