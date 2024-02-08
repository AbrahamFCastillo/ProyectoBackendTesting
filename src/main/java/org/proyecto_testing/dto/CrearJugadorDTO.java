package org.proyecto_testing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CrearJugadorDTO { 
    //validacion spring
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El nickname es obligatorio")
    private String nickname;
    @NotBlank(message = "El Email es obligatorio")
    private String email;
    @NotBlank(message = "La contrasena es obligatoria")
    private String password;
}

