/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.Paciente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pablo
 */
public class PacienteMapper {

    /**
     * Convierte un PacienteNuevoDTO a una entidad Paciente
     */
    public Paciente toEntity(PacienteNuevoDTO paciNuevo) {
        if (paciNuevo == null) {
            return null;
        }
        return new Paciente(
                paciNuevo.getNombre(),
                paciNuevo.getApellidoPaterno(),
                paciNuevo.getApellidoMaterno(),
                paciNuevo.getFechaNacimiento(),
                paciNuevo.getCalle(),
                paciNuevo.getColonia(),
                paciNuevo.getNumero(),
                paciNuevo.getTelefono(),
                paciNuevo.getCorreo(),
                paciNuevo.getIdUsuario()
        );
    }
    
    public Paciente toEntityV(PacienteViejoDTO paciViejo) {
        if (paciViejo == null) {
            return null;
        }
        return new Paciente(
                paciViejo.getNombre(),
                paciViejo.getApellidoPaterno(),
                paciViejo.getApellidoMaterno(),
                paciViejo.getFechaNacimiento(),
                paciViejo.getCalle(),
                paciViejo.getColonia(),
                paciViejo.getNumero(),
                paciViejo.getTelefono(),
                paciViejo.getCorreo(),
                paciViejo.getId_paciente()
        );
    }

    /**
     * Convierte una entidad Paciente a un PacienteNuevoDTO
     */
    public PacienteNuevoDTO toNuevoDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteNuevoDTO(
                paciente.getNombre(),
                paciente.getApellidoPaterno(),
                paciente.getApellidoMaterno(),
                paciente.getFechaNacimiento(),
                paciente.getCalle(),
                paciente.getColonia(),
                paciente.getNumero(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getIdUsuario()
        );
    }

    /**
     * Convierte una entidad Paciente a un PacienteViejoDTO (incluyendo ID)
     */
    public PacienteViejoDTO toViejoDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteViejoDTO(
                String.valueOf(paciente.getIdPaciente()), // Convertimos el ID a String
                paciente.getNombre(),
                paciente.getApellidoPaterno(),
                paciente.getApellidoMaterno(),
                paciente.getFechaNacimiento(),
                paciente.getCalle(),
                paciente.getColonia(),
                paciente.getNumero(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getIdUsuario()
        );
    }

    /**
     * Convierte una lista de entidades Paciente a una lista de DTOs
     * PacienteViejoDTO
     */
    public List<PacienteViejoDTO> toViejoDTOList(List<Paciente> listaPacientes) {
        if (listaPacientes == null || listaPacientes.isEmpty()) {
            return new ArrayList<>();
        }

        List<PacienteViejoDTO> listaDTO = new ArrayList<>();
        for (Paciente paciente : listaPacientes) {
            listaDTO.add(toViejoDTO(paciente));
        }
        return listaDTO;
    }

    /**
     * Convierte una lista de entidades Paciente a una lista de DTOs
     * PacienteNuevoDTO
     */
    public List<PacienteNuevoDTO> toNuevoDTOList(List<Paciente> listaPacientes) {
        if (listaPacientes == null || listaPacientes.isEmpty()) {
            return new ArrayList<>();
        }

        List<PacienteNuevoDTO> listaDTO = new ArrayList<>();
        for (Paciente paciente : listaPacientes) {
            listaDTO.add(toNuevoDTO(paciente));
        }
        return listaDTO;
    }
}
