/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Usuario;
import Entidades.Usuario.TipoUsuario;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Beto_
 */
public class UsuarioDAO implements IUsuarioDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException {
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO usuarios (correo, cedulaProfesional, contrasenia, tipo)VALUES (?, ?, ?, ?)";

        //Encriptamos la contraseña antes de guardarla
        String contraseniaEncriptada = BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt());

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros de la consulta
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getCedulaProfesional());
            ps.setString(3, contraseniaEncriptada);
            ps.setObject(4, usuario.getTipo().toString(), Types.VARCHAR);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                logger.severe("La creación del usuario falló");
                throw new PersistenciaException("La creación del usuario falló");
            }
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setIdUsuario(generatedKeys.getInt(1));
                    logger.info("Usuario creado exitosamente con ID: " + usuario.getIdUsuario());
                } else {
                    logger.severe("La creación del usuario falló, no se obtuvo ID.");
                    throw new PersistenciaException("La creación del usuario falló, no se obtuvo ID.");
                }
            }
            return usuario;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear usuario", e);
            throw new PersistenciaException("Error al crear al usuario", e);
        }
    }

    @Override
    public Usuario iniciarSesionPaciente(String correo, String contrasenia) throws PersistenciaException {
        try {
            Usuario usuario = obtenerUsuarioPorCorreo(correo);
            String contraseniaEncriptada = usuario.getContrasenia();
            if (BCrypt.checkpw(contrasenia, contraseniaEncriptada)) {
                return usuario;
            } else {
                throw new PersistenciaException("Error: la contraseña no coincide con el correo");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al iniciarSesion ", e);
            throw new PersistenciaException("Error al iniciar sesión ", e);
        }
    }

    @Override
    public Usuario iniciarSesionMedico(String cedula, String contrasenia) throws PersistenciaException {
        try {
            Usuario usuario = obtenerUsuarioPorCedula(cedula);
            String contraseniaBD = usuario.getContrasenia();
            if (contrasenia.equals(contraseniaBD)) {
                return usuario;
            } else {
                throw new PersistenciaException("Error: la contraseña no coincide con la cedula");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al iniciarSesion ", e);
            throw new PersistenciaException("Error al iniciar sesión ", e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) throws PersistenciaException {
        // auxiliar de usuario
        Usuario usuario = null;
        String tipo;

        String consultaSQL = "SELECT id, correo, cedulaProfesional, contrasenia, tipo FROM usuarios WHERE correo = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setString(1, correo);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto activista y se asignan sus propiedades
                    usuario = new Usuario(); // es el que definimos al inicio
                    usuario.setIdUsuario(rs.getInt("id"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setCedulaProfesional(rs.getString("cedulaProfesional"));
                    usuario.setContrasenia(rs.getString("contrasenia"));

                    tipo = rs.getString("tipo");
                    if (tipo.equals("paciente")) {
                        usuario.setTipo(TipoUsuario.paciente);
                    } else {
                        usuario.setTipo(TipoUsuario.medico);
                    }

                    logger.info("Usuario encontrado: " + usuario);
                } else {
                    logger.warning("No se encontró usuario con correo: " + correo); // no es error, solo advertencia
                    throw new PersistenciaException("No se encontró un registro con este correo");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar usuario con correo: " + correo, e);
            throw new PersistenciaException("Error al consultar usuario por correo " + correo, e);

        }
        return usuario;
    }

    @Override
    public Usuario obtenerUsuarioPorCedula(String cedula) throws PersistenciaException {
        // auxiliar de usuario
        Usuario usuario = null;
        String tipo;

        String consultaSQL = "SELECT id, correo, cedulaProfesional, contrasenia, tipo FROM usuarios WHERE cedulaProfesional = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setString(1, cedula);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto activista y se asignan sus propiedades
                    usuario = new Usuario(); // es el que definimos al inicio
                    usuario.setIdUsuario(rs.getInt("id"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setCedulaProfesional(rs.getString("cedulaProfesional"));
                    usuario.setContrasenia(rs.getString("contrasenia"));

                    tipo = rs.getString("tipo");
                    if (tipo.equals("paciente")) {
                        usuario.setTipo(TipoUsuario.paciente);
                    } else {
                        usuario.setTipo(TipoUsuario.medico);
                    }

                    logger.info("Usuario encontrado: " + usuario);
                } else {
                    logger.warning("No se encontró usuario con cedula: " + cedula); // no es error, solo advertencia
                    throw new PersistenciaException("No se encontró un registro con esta cedula ");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar usuario con cedula: " + cedula, e);
            throw new PersistenciaException("Error al consultar usuario por cedula " + cedula, e);

        }
        return usuario;
    }

    @Override
    public List<Usuario> obtenerUsuarios() throws PersistenciaException {
        String consultaSQL = "SELECT id, correo, cedulaProfesional, contrasenia, tipo FROM usuarios";

        // Lista donde se almacenarán los usuarios recuperados
        List<Usuario> usuarios = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL); ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
                ) {
            // Se recorre el ResultSet mientras haya filas disponibles con el next()
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                TipoUsuario tipoUsuario;
                if (tipo.equals("paciente")) {
                    tipoUsuario = TipoUsuario.paciente;
                } else {
                    tipoUsuario = TipoUsuario.medico;
                }

                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("correo"),
                        rs.getString("cedulaProfesional"),
                        rs.getString("contrasenia"),
                        tipoUsuario
                );

                // Se agrega el usuario a la lista
                usuarios.add(usuario);
            }

            // Se retorna la lista con todos los usuarios obtenidos
            return usuarios;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error al obtener la lista de usuarios.", ex);
        }
    }

    @Override
    public boolean verificarContra(String texto) throws SQLException, PersistenciaException {
        String consultaSQL = "SELECT * FROM USUARIOS WHERE CONTRASENIA = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {
            ps.setString(1, texto);  // Establecer la cédula a buscar

            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();

            // Verificar si la cédula existe
            if (rs.next()) {
                // Si hay un resultado, la cédula existe
                return true;
            } else {
                // Si no hay resultado, la cédula no existe
                return false;
            }
        }
    }
}
