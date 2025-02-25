/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IUsuarioDAO;
import DAO.UsuarioDAO;
import DTO.UsuarioNuevoDTO;
import DTO.UsuarioViejoDTO;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.UsuarioMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class UsuarioBO {
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IUsuarioDAO usuarioDAO;
    private final UsuarioMapper mapper = new UsuarioMapper(); // Usamos el mapper

    public UsuarioBO(IConexionBD conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
    }
    
    public boolean agregarUsuario(UsuarioNuevoDTO usuarioDTO) throws NegocioException { //Funciona
        if (usuarioDTO == null) {
            throw new NegocioException("El usuario no puede ser nulo.");
        }

        //validaciones de espacios vacíos
        if (usuarioDTO.getCorreo().isEmpty() || usuarioDTO.getContrasenia().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }
        
        // Convertimos el DTO a la entidad
        Usuario usuario = mapper.toEntity(usuarioDTO);

        try {
            Usuario usuarioGuardado = usuarioDAO.agregarUsuario(usuario);
            return usuarioGuardado != null;
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al guardar usuario en la Base de Datos", ex);
            throw new NegocioException("Hubo un error al guardar el usuario.", ex);
        }
    }
    
    public UsuarioViejoDTO iniciarSesionPaciente(String correo, String contrasenia) throws NegocioException{
        try {
            return mapper.toViejoDTO(usuarioDAO.iniciarSesionPaciente(correo, contrasenia));
        } catch (PersistenciaException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public UsuarioViejoDTO iniciarSesionMedico(String cedula, String contrasenia) throws NegocioException{
        try {
            return mapper.toViejoDTO(usuarioDAO.iniciarSesionMedico(cedula, contrasenia));
        } catch (PersistenciaException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public UsuarioViejoDTO obtenerUsuarioPorCorreo(String correo) throws NegocioException{  //Funciona
        if (correo.isEmpty()) {
            throw new NegocioException("El usuario no puede ser nulo.");
        }

        try {
            Usuario usuarioEncontrado = usuarioDAO.obtenerUsuarioPorCorreo(correo);
            return mapper.toViejoDTO(usuarioEncontrado);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al guardar usuario en la Base de Datos", ex);
            throw new NegocioException("Hubo un error al guardar el usuario.", ex);
        }
    }
}
