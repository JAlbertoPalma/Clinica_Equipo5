/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.UsuarioNuevoDTO;
import DTO.UsuarioViejoDTO;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Beto_
 */
public class UsuarioMapper {
    
    /**
     * Convierte un UsuarioNuevoDTO a una entidad Usuario
     */
    public Usuario toEntity(UsuarioNuevoDTO usuarioNuevo) {
        if (usuarioNuevo == null) {
            return null;
        }
        return new Usuario(
                usuarioNuevo.getCorreo(),
                usuarioNuevo.getCedulaProfesional(),
                usuarioNuevo.getContrasenia(),
                usuarioNuevo.getTipo()
        );
    }

    /**
     * Convierte una entidad Usuario a un UsuarioNuevoDTO
     */
    public UsuarioNuevoDTO toNuevoDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioNuevoDTO(
                usuario.getCorreo(),
                usuario.getCedulaProfesional(),
                usuario.getContrasenia(),
                usuario.getTipo()
        );
    }

    /**
     * Convierte una entidad Paciente a un PacienteViejoDTO (incluyendo ID)
     */
    public UsuarioViejoDTO toViejoDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioViejoDTO(
                String.valueOf(usuario.getIdUsuario()), // Convertimos el ID a String
                usuario.getCorreo(),
                usuario.getCedulaProfesional(),
                usuario.getContrasenia(),
                usuario.getTipo()
        );
    }

    /**
     * Convierte una lista de entidades Paciente a una lista de DTOs
     * PacienteViejoDTO
     */
    public List<UsuarioViejoDTO> toViejoDTOList(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            return new ArrayList<>();
        }

        List<UsuarioViejoDTO> listaDTO = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            listaDTO.add(toViejoDTO(usuario));
        }
        return listaDTO;
    }

    /**
     * Convierte una lista de entidades Paciente a una lista de DTOs
     * PacienteNuevoDTO
     */
    public List<UsuarioNuevoDTO> toNuevoDTOList(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            return new ArrayList<>();
        }

        List<UsuarioNuevoDTO> listaDTO = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            listaDTO.add(toNuevoDTO(usuario));
        }
        return listaDTO;
    }
}
