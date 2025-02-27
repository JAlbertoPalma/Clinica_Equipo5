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
 * Clase de Lógica de Negocio para la gestión de usuarios.
 * @author Beto_
 */
public class UsuarioBO {
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IUsuarioDAO usuarioDAO;
    private final UsuarioMapper mapper = new UsuarioMapper(); // Usamos el mapper

    /**
     * Constructor de UsuarioBO.
     * Inicializa el objeto UsuarioDAO con la conexión a la base de datos proporcionada.
     *
     * @param conexion Objeto IConexionBD para la conexión a la base de datos.
     */
    public UsuarioBO(IConexionBD conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
    }
    
    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param usuarioDTO DTO con los datos del usuario a agregar.
     * @return true si la adición fue exitosa, false en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso de adición.
     */
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
    
    /**
     * Inicia sesión de un usuario.
     *
     * @param entrada Correo electrónico o cédula profesional del usuario.
     * @param contrasenia Contraseña del usuario.
     * @return DTO con los datos del usuario si la autenticación es exitosa.
     * @throws NegocioException Si ocurre un error durante el proceso de autenticación.
     */
    public UsuarioViejoDTO iniciarSesion(String entrada, String contrasenia) throws NegocioException{
        try {
            return mapper.toViejoDTO(usuarioDAO.iniciarSesion(entrada, contrasenia));
        } catch (PersistenciaException ex) {
            Logger.getLogger(UsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Obtiene los datos de un usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a obtener.
     * @return DTO con los datos del usuario.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public UsuarioViejoDTO obtenerUsuarioPorCorreo(String correo) throws NegocioException{  //Funciona
        if (correo.isEmpty()) {
            throw new NegocioException("El usuario no puede ser nulo.");
        }

        try {
            Usuario usuarioEncontrado = usuarioDAO.obtenerUsuarioPorCorreo(correo);
            return mapper.toViejoDTO(usuarioEncontrado);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener usuario en la Base de Datos", ex);
            throw new NegocioException("Hubo un error al obtener el usuario.", ex);
        }
    }
}
