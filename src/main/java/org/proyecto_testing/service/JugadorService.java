package org.proyecto_testing.service;


import java.util.List;
import java.util.Optional;

import org.proyecto_testing.dto.ActualizarJugadorDTO;
import org.proyecto_testing.dto.CrearJugadorDTO;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.mapper.JugadorMapper;
import org.proyecto_testing.model.Jugador;
import org.proyecto_testing.persistence.JugadorRepository;
import org.proyecto_testing.exception.JugadorNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
//import jakarta.transaction.Transactional;
 
@Service
public class JugadorService {
    
    @Autowired
    private JugadorRepository repositorio;
    @Autowired
    private JugadorMapper mapper;

    //Obtener todos los registros jugador
    public List<JugadorDTO> findAll() {
        List<Jugador> data = repositorio.findAll();
        return mapper.toDTO(data);
    }

    //@Transactional
    @SuppressWarnings("null")
    public JugadorDTO guardar(CrearJugadorDTO datos) {
        Jugador guardado = repositorio.save(mapper.toModel(datos));
        
        return mapper.toDTO(guardado);
    }

    @SuppressWarnings("null")
    public void actualizar(long id, ActualizarJugadorDTO datos) throws JugadorNoEncontradoException {
        Optional<Jugador> resultado = repositorio.findById(id);

        if (resultado.isEmpty()) 
            throw new JugadorNoEncontradoException(id);

        Jugador modelo = resultado.get();

        mapper.actualizar(modelo, datos);

        repositorio.save(modelo);
    }

    public void eliminarPorId(long id) {
        repositorio.deleteById(id);
    }
}

