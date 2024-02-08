package org.proyecto_testing.dto;

import lombok.Data;

//Como retornar la data
@Data
public class JugadorDTO { 
    private long id;
    private String nombre;
    private String apellido;
    private String nickname;
    private String email;
    private String password;
}