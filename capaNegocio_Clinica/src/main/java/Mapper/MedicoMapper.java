/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.MedicoNuevoDTO;
import DTO.MedicoViejoDTO;
import DTO.UsuarioNuevoDTO;
import DTO.UsuarioViejoDTO;
import Entidades.Medico;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pablo
 */
public class MedicoMapper {
    /**
     * Convierte un MedicoNuevoDTO a una entidad Usuario
     */
    public Medico toEntity(MedicoNuevoDTO medicoNuevo) {
        if (medicoNuevo == null) {
            return null;
        }
        return new Medico(
                medicoNuevo.getNombre(),
                medicoNuevo.getApellidoPaterno(),
                medicoNuevo.getApellidoMaterno(),
                medicoNuevo.getEspecialidad(),
                medicoNuevo.getCedulaProfesional(),
                medicoNuevo.isEstaActivo(),
                medicoNuevo.getIdUsuario()
        );
    }

    /**
     * Convierte una entidad medico a un MedicoNuevoDTO
     */
    public MedicoNuevoDTO toNuevoDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return new MedicoNuevoDTO(
                medico.getNombre(),
                medico.getApellidoPaterno(),
                medico.getApellidoMaterno(),
                medico.getEspecialidad(),
                medico.getCedulaProfesional(),
                medico.isEstaActivo(),
                medico.getIdUsuario()
        );
    }

    /**
     * Convierte una entidad medico a un MedicoViejoDTO (incluyendo ID)
     */
    public MedicoViejoDTO toViejoDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return new MedicoViejoDTO(
                medico.getIdMedico(), // Convertimos el ID a String
                medico.getNombre(),
                medico.getApellidoPaterno(),
                medico.getApellidoMaterno(),
                medico.getEspecialidad(),
                medico.getCedulaProfesional(),
                medico.isEstaActivo(),
                medico.getIdUsuario()
        );
    }

    /**
     * Convierte una lista de entidades Medico a una lista de DTOs
     * MedicoViejoDTO
     */
    public List<MedicoViejoDTO> toViejoDTOList(List<Medico> listaMedicos) {
        if (listaMedicos == null || listaMedicos.isEmpty()) {
            return new ArrayList<>();
        }

        List<MedicoViejoDTO> listaDTO = new ArrayList<>();
        for (Medico medico : listaMedicos) {
            listaDTO.add(toViejoDTO(medico));
        }
        return listaDTO;
    }

    /**
     * Convierte una lista de entidades Medico a una lista de DTOs
     * MedicoNuevoDTO
     */
    public List<MedicoNuevoDTO> toNuevoDTOList(List<Medico> listaMedico) {
        if (listaMedico == null || listaMedico.isEmpty()) {
            return new ArrayList<>();
        }

        List<MedicoNuevoDTO> listaDTO = new ArrayList<>();
        for (Medico medico : listaMedico) {
            listaDTO.add(toNuevoDTO(medico));
        }
        return listaDTO;
    }
}
