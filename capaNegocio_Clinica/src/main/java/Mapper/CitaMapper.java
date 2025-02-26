/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import DTO.ConsultaUrgenciaDTO;
import Entidades.Cita;
import Entidades.ConsultaUrgencia;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Beto_
 */
public class CitaMapper {
    /**
     * Convierte un MedicoNuevoDTO a una entidad Usuario
     */
    public Cita toEntity(CitaNuevaDTO citaNueva) {
        if (citaNueva == null) {
            return null;
        }
        return new Cita(
                citaNueva.getIdMedico(),
                citaNueva.getIdPaciente(),
                citaNueva.getFecha(),
                citaNueva.getHoraInicio(),
                citaNueva.getHoraFin(),
                citaNueva.getEstado()
        );
    }

    /**
     * Convierte una entidad medico a un MedicoNuevoDTO
     */
    public CitaNuevaDTO toNuevoDTO(Cita cita) {
        if (cita == null) {
            return null;
        }
        return new CitaNuevaDTO(
                cita.getIdMedico(),
                cita.getIdPaciente(),
                cita.getFecha(),
                cita.getHoraInicio(),
                cita.getHoraFin(),
                cita.getEstado()
        );
    }

    /**
     * Convierte una entidad medico a un MedicoViejoDTO (incluyendo ID)
     */
    public CitaViejaDTO toViejoDTO(Cita cita) {
        if (cita == null) {
            return null;
        }
        return new CitaViejaDTO(
                cita.getIdCita(),
                cita.getIdMedico(),
                cita.getIdPaciente(),
                cita.getFecha(),
                cita.getHoraInicio(),
                cita.getHoraFin(),
                cita.getEstado()
        );
    }

    /**
     * Convierte una lista de entidades Medico a una lista de DTOs
     * MedicoViejoDTO
     */
    public List<CitaViejaDTO> toViejoDTOList(List<Cita> listaCitas) {
        if (listaCitas == null || listaCitas.isEmpty()) {
            return new ArrayList<>();
        }

        List<CitaViejaDTO> listaDTO = new ArrayList<>();
        for (Cita cita : listaCitas) {
            listaDTO.add(toViejoDTO(cita));
        }
        return listaDTO;
    }

    /**
     * Convierte una lista de entidades Medico a una lista de DTOs
     * MedicoNuevoDTO
     */
    public List<CitaNuevaDTO> toNuevoDTOList(List<Cita> listaCitas) {
        if (listaCitas == null || listaCitas.isEmpty()) {
            return new ArrayList<>();
        }

        List<CitaNuevaDTO> listaDTO = new ArrayList<>();
        for (Cita cita : listaCitas) {
            listaDTO.add(toNuevoDTO(cita));
        }
        return listaDTO;
    }
    
    /**
     * Convierte una entidad medico a un MedicoViejoDTO (incluyendo ID)
     */
    public ConsultaUrgenciaDTO toUrgenciaDTO(ConsultaUrgencia consultaUrgencia) {
        if (consultaUrgencia == null) {
            return null;
        }
        return new ConsultaUrgenciaDTO(
                consultaUrgencia.getNombreMedico(),
                consultaUrgencia.getHoraInicioConsulta(),
                consultaUrgencia.getHoraFinConsulta(),
                consultaUrgencia.getFolio()
        );
    }
}
