package org.proyecto_testing.jugadores.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.proyecto_testing.controller.JugadorController;
import org.proyecto_testing.dto.ActualizarJugadorDTO;
import org.proyecto_testing.dto.CrearJugadorDTO;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.LinkedList; 

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JugadorControllerTest {
    @MockBean
    private JugadorService servicio;

    @Autowired
    private JugadorController controlador;

    @Test
    @DisplayName("El controlador debería ser inyectado")
    void smokeTest() {
        assertNotNull(controlador);
    }

    @Test
    @DisplayName("El controlador debería devolver una lista de jugadores")
    void findAllTest() {
        List<JugadorDTO> datos = new LinkedList<>();

        JugadorDTO jugador = new JugadorDTO();

        jugador.setId(1);
        jugador.setNombre("Juan");
        jugador.setApellido("Flores");
        jugador.setEmail("abb@ejemplo.com");
        jugador.setNickname("jjF");
        jugador.setPassword("123456abc");

        datos.add(jugador);

        when(servicio.findAll()).thenReturn(datos);

        List<JugadorDTO> resultado = controlador.findAll();

        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        //Comparar id del jugador con el primer elemento de la lista resultado
        assertEquals(jugador.getId(), resultado.get(0).getId());
        assertEquals(jugador.getNombre(), resultado.get(0).getNombre());
        assertEquals(jugador.getApellido(), resultado.get(0).getApellido());
        assertEquals(jugador.getEmail(), resultado.get(0).getEmail());
        assertEquals(jugador.getNickname(), resultado.get(0).getNickname());
        assertEquals(jugador.getPassword(), resultado.get(0).getPassword());
    }

    @Test
    @DisplayName("El controlador debería guardar un jugador")
    void guardarTest() {
        CrearJugadorDTO dto = new CrearJugadorDTO();

        dto.setNombre("Juan");
        dto.setApellido("Flores");
        dto.setEmail("abb@ejemplo.com");
        dto.setNickname("jjF");
        dto.setPassword("123456abc");

        JugadorDTO jugador = new JugadorDTO();
        
        jugador.setId(200);
        jugador.setNombre(dto.getNombre());
        jugador.setApellido(dto.getApellido());
        jugador.setEmail(dto.getEmail());
        jugador.setNickname(dto.getNickname());
        jugador.setPassword(dto.getPassword());

        when(servicio.guardar(any(CrearJugadorDTO.class))).thenReturn(jugador);

        JugadorDTO resultado = controlador.guardar(dto);

        assertNotNull(resultado);
        assertEquals(jugador.getId(), resultado.getId());
        assertEquals(jugador.getNombre(), resultado.getNombre());
        assertEquals(jugador.getApellido(), resultado.getApellido());
        assertEquals(jugador.getEmail(), resultado.getEmail());
        assertEquals(jugador.getNickname(), resultado.getNickname());
        assertEquals(jugador.getPassword(), resultado.getPassword());
    }

     @Test
     @DisplayName("El controlador debería actualizar un jugador")
     void actualizarTest() {
        ActualizarJugadorDTO dto = new ActualizarJugadorDTO();

        dto.setNombre("Juan");
        dto.setApellido("Flores");
        dto.setEmail("abb@ejemplo.com");
        dto.setNickname("jjF");
        dto.setPassword("123456abc");

        controlador.actualizar(300, dto);
        // Verificando que el método update del servicio
        // haya sido ejecutado 1 vez
        verify(servicio, times(1)).actualizar(300, dto);
     }

     @Test 
    @DisplayName("El controlador debería eliminar un jugador")
    void eliminarTest() {
        controlador.eliminar(8793);

        verify(servicio, times(1)).eliminarPorId(8793);
  }
}
