package org.proyecto_testing.jugadores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.proyecto_testing.dto.ActualizarJugadorDTO;
import org.proyecto_testing.dto.CrearJugadorDTO;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.exception.JugadorNoEncontradoException;
import org.proyecto_testing.model.Jugador;
import org.proyecto_testing.persistence.JugadorRepository;
import org.proyecto_testing.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JugadorServiceTest {

    @MockBean
    private JugadorRepository repositorio;

    @Autowired
    private JugadorService servicio;

    @Test
    @DisplayName("El servicio debería ser inyectado")
    void smokeTest() {
        assertNotNull(servicio);
    }

    @Test
    @DisplayName("El servicio debería devolver los jugadores del repositorio")
    void findAllTest() {
        List<Jugador> datos = new LinkedList<>();

        Jugador jugador = new Jugador();
        
        jugador.setId(1);
        jugador.setNombre("Juan");
        jugador.setApellido("Flores");
        jugador.setEmail("abb@ejemplo.com");
        jugador.setNickname("jjF");
        jugador.setPassword("123456abc");

        datos.add(jugador);

        when(repositorio.findAll()).thenReturn(datos);

        List<JugadorDTO> resultado = servicio.findAll();

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

    @SuppressWarnings("null")
    @Test
    @DisplayName("El servicio debería guardar un jugador en el repositorio")
    void saveTest() {
        CrearJugadorDTO dto = new CrearJugadorDTO();

        dto.setNombre("Juan");
        dto.setApellido("Flores");
        dto.setEmail("abb@ejemplo.com");
        dto.setNickname("jjF");
        dto.setPassword("123456abc");

        Jugador modeloJugador = new Jugador();

        modeloJugador.setId(200);
        modeloJugador.setNombre(dto.getNombre());
        modeloJugador.setApellido(dto.getApellido());
        modeloJugador.setEmail(dto.getEmail());
        modeloJugador.setNickname(dto.getNickname());
        modeloJugador.setPassword(dto.getPassword());
        
        when(repositorio.save(any(Jugador.class))).thenReturn(modeloJugador);

        JugadorDTO resultado = servicio.guardar(dto);

        assertNotNull(resultado);
        assertEquals(modeloJugador.getId(), resultado.getId());
        assertEquals(modeloJugador.getNombre(), resultado.getNombre());
        assertEquals(modeloJugador.getApellido(), resultado.getApellido());
        assertEquals(modeloJugador.getEmail(), resultado.getEmail());
        assertEquals(modeloJugador.getNickname(), resultado.getNickname());
        assertEquals(modeloJugador.getPassword(), resultado.getPassword());
    }

    @Test
    @DisplayName("El servicio debería lanzar un error si el jugador no ha sido encontrado")
    void actualizarConErrorTest() {
        ActualizarJugadorDTO dto = new ActualizarJugadorDTO();
        //Evitar que el objeto retornado sea nulo
        Optional<Jugador> aux = Optional.empty();

        when(repositorio.findById(anyLong())).thenReturn(aux);

        assertThrows(JugadorNoEncontradoException.class, () -> servicio.actualizar(100, dto));
    }

    @Test
    @DisplayName("El servicio debería actualizar un jugador en el repositorio")
    void actualizarTest() {
        ActualizarJugadorDTO dto = new ActualizarJugadorDTO();

        dto.setNombre("Juan");
        dto.setApellido("Flores");
        dto.setEmail("abb@ejemplo.com");
        dto.setNickname("jjF");
        dto.setPassword("123456abc");

        Jugador jugador = new Jugador();

        jugador.setId(234);
        jugador.setNombre("John");
        jugador.setApellido("Juarez");
        jugador.setEmail("otro@ejemplo.com");
        jugador.setNickname("jjj");
        jugador.setPassword("qwerty789");

        when(repositorio.findById(anyLong())).thenReturn(Optional.of(jugador));

        servicio.actualizar(234, dto);

        assertEquals(dto.getNombre(), jugador.getNombre());
        assertEquals(dto.getApellido(), jugador.getApellido());
        assertEquals(dto.getEmail(), jugador.getEmail());
        assertEquals(dto.getNickname(), jugador.getNickname());
        assertEquals(dto.getPassword(), jugador.getPassword());
        verify(repositorio, times(1)).save(jugador);
    }

    @Test
    @DisplayName("El servicio debería eliminar un jugador por id en el repositorio")
    void deleteByIdTest() {
        servicio.eliminarPorId(233l);

        verify(repositorio, times(1)).deleteById(233l);
    }
}
