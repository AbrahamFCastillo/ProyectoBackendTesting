package org.proyecto_testing.jugadores.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.model.Jugador;
import org.proyecto_testing.persistence.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
class JugadorControllerTestE2E {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JugadorRepository repositorio;

    private ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void setup() {
        repositorio.deleteAll();
    }

    @Test
    @DisplayName("GET/jugadores debería devolver una lista vacía")
    void listaVaciaTest() throws Exception {
        // Realizar una petición de tipo GET
        // hacia /movies y esperar que el resultado sea 200
        MvcResult resultado = mockMvc.perform(get("/jugadores"))
        .andExpect(status().isOk())
        .andReturn();

        String contenido = resultado.getResponse().getContentAsString();

        assertEquals("[]", contenido);
    }

    @Test
    @DisplayName("GET/jugadores debería devolver una lista con todos los jugadores")
    void devolverTodoTest() throws Exception {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
    
        jugador1.setNombre("Juan");
        jugador1.setApellido("Flores");
        jugador1.setEmail("juanf@ejemplo.com");
        jugador1.setNickname("jjF");
        jugador1.setPassword("123456abc");

        jugador2.setNombre("Nelva");
        jugador2.setApellido("Espinoza");
        jugador2.setEmail("nelva@ejemplo.com");
        jugador2.setNickname("nesss");
        jugador2.setPassword("123456abc");

        repositorio.save(jugador1);
        repositorio.save(jugador2);

        MvcResult resultado = mockMvc.perform(get("/jugadores"))
            .andExpect(status().isOk())
            .andReturn();

        String contenido = resultado.getResponse().getContentAsString();

        // Referencia del tipo de dato al que se va a convertir el JSON
        TypeReference<List<JugadorDTO>> listaTypeReference = new TypeReference<List<JugadorDTO>>() {};

        // Conversion de JSON a un objeto Java
        List<JugadorDTO> respuesta = mapper.readValue(contenido, listaTypeReference);

        assertTrue(respuesta.size() == 2);
        assertEquals(jugador1.getNombre(), respuesta.get(0).getNombre());
        assertEquals(jugador2.getNombre(), respuesta.get(1).getNombre());
    }

    /* 
     * Prueba que lanza un error si falta un atributo; en este caso, el nombre. 
     * Dado que se obtiene el mismo resultado al probar con todos los atributos, no se consideró necesario
     * realizar una prueba individual por cada uno.
    */
    @Test
    @DisplayName("POST/jugadores debería devolver un error si falta el nombre")
    void atributoFaltanteEnRequestBodyTest() throws Exception {
        MvcResult resultado = mockMvc.perform(post("/jugadores").contentType("application/json")
            .content("{\"apellido\":\"Espinoza\",\"nickname\":\"ness\",\"email\":\"nelva@ejemplo.com\",\"password\":\"sdsfd\"}"))
            .andExpect(status().isBadRequest())
            .andReturn();

        String contenido = resultado.getResponse().getContentAsString();

        String respuestaEsperada = "{\"codigo\":\"ERR_VALID\",\"mensaje\":\"Existen errrores en los datos de entrada\",\"detalles\":[\"El nombre es obligatorio\"]}";
        assertEquals(respuestaEsperada, contenido);
    }

}
