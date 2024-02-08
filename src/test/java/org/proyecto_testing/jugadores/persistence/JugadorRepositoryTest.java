package org.proyecto_testing.jugadores.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.proyecto_testing.model.Jugador;
import org.proyecto_testing.persistence.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List; 

/* Prueba de Integración
 *Se utiliza una BD de prueba H2
 */
@DataJpaTest //Permite reconocer y configurar la BD de prueba (H2 en este caso)
@AutoConfigureTestDatabase(replace = Replace.NONE) //Usar BD de prueba
class JugadorRepositoryTest {

    @Autowired
    private JugadorRepository repositorio;

    @Autowired
    private TestEntityManager manager;

    @Test 
    @DisplayName("El repositorio debería ser inyectado")
    void smokeTest() {
        assertNotNull(repositorio);
    }

    @Test
    @DisplayName("El repositorio debería filtrar jugadores por email")
    void findByEmailTest() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        Jugador jugador3 = new Jugador();

        jugador1.setNombre("Juan");
        jugador1.setApellido("Flores");
        jugador1.setEmail("lbtran@ejemplo.com");
        jugador1.setNickname("jjF");
        jugador1.setPassword("123456abc");

        jugador2.setNombre("Luis");
        jugador2.setApellido("Beltran");
        jugador2.setEmail("lbtran@ejemplo.com");
        jugador2.setNickname("llfr");
        jugador2.setPassword("4dggdb");

        jugador3.setNombre("Nelva");
        jugador3.setApellido("Espinoza");
        jugador3.setEmail("nelva@ejemplo.com");
        jugador3.setNickname("nesss");
        jugador3.setPassword("123456abc");

        manager.persist(jugador1);
        manager.persist(jugador2);
        manager.persist(jugador3);

        List<Jugador> resultado = repositorio.findByEmail("lbtran@ejemplo.com");
       
        assertTrue(resultado.size() == 2);
    }

    @Test
    @DisplayName("El repositorio debería filtrar jugadores por nombre")
    void findByNombreTest() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        Jugador jugador3 = new Jugador();

        jugador1.setNombre("Juan");
        jugador1.setApellido("Flores");
        jugador1.setEmail("abb1@ejemplo.com");
        jugador1.setNickname("jjF");
        jugador1.setPassword("123456abc");

        jugador2.setNombre("Juan");
        jugador2.setApellido("Beltran");
        jugador2.setEmail("abb1@ejemplo.com");
        jugador2.setNickname("llfr");
        jugador2.setPassword("4dggdb");

        jugador3.setNombre("Luis");
        jugador3.setApellido("Lopez");
        jugador3.setEmail("otro@ejemplo.com");
        jugador3.setNickname("kmmo");
        jugador3.setPassword("123456abc");

        manager.persist(jugador1);
        manager.persist(jugador2);
        manager.persist(jugador3);

        List<Jugador> resultado = repositorio.findByNombre("Juan");
       
        assertTrue(resultado.size() == 2);
    }
}
