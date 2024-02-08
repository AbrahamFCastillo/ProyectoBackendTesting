package org.proyecto_testing.mapper;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.proyecto_testing.dto.ActualizarJugadorDTO;
import org.proyecto_testing.dto.CrearJugadorDTO;
import org.proyecto_testing.dto.JugadorDTO;
import org.proyecto_testing.model.Jugador;

//Ignorar los atributos nulos
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JugadorMapper {
    JugadorDTO toDTO(Jugador modelo);

    List<JugadorDTO> toDTO(List<Jugador> modelos);
  
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Jugador toModel(CrearJugadorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void actualizar(@MappingTarget Jugador modelo, ActualizarJugadorDTO dto);
}