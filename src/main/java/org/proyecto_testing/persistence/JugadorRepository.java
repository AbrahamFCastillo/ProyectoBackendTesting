package org.proyecto_testing.persistence;

import java.util.List; 

import org.proyecto_testing.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    //List<Jugador> findAll();

    List<Jugador> findByEmail(String email);
    List<Jugador> findByNombre(String nombre);

}
