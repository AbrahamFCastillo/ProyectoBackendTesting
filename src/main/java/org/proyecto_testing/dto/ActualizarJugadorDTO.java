package org.proyecto_testing.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ActualizarJugadorDTO {
    //No colocar @NotBlank, los campos pueden estar vacios. Es una actualizacion
    private String nombre;
    private String apellido;
    private String nickname;
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;
    private String password;
}
