package org.proyecto_testing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.proyecto_testing.dto.ActualizarJugadorDTO;
import org.proyecto_testing.dto.CrearJugadorDTO;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.exception.JugadorNoEncontradoException;
import org.proyecto_testing.service.JugadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("jugadores")
public class JugadorController {

    @Autowired
    private JugadorService servicio;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JugadorDTO> findAll() {
        return servicio.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JugadorDTO guardar(@Valid @RequestBody CrearJugadorDTO datos) {
        return servicio.guardar(datos);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizar(@PathVariable long id, @Valid @RequestBody ActualizarJugadorDTO data) throws JugadorNoEncontradoException {
        servicio.actualizar(id, data);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable long id) {
        servicio.eliminarPorId(id);
    }
    
}
