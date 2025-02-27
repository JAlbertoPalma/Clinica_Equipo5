/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
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
 *  Clase DAO para la gestión de usuarios en la base de datos.
 * Implementa la interfaz IUsuarioDAO.
 * @author Beto_
 */
public class UsuarioDAO implements IUsuarioDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    /**
     * Constructor vacío, inicializa la conexión a la bd
     * @param conexion la conexión con la base de datos
     */
    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Agrega un nuevo usuario a la base de datos, encriptando la contraseña antes de guardarla.
     *
     * @param usuario Objeto Usuario con los datos del usuario a agregar.
     * @return Objeto Usuario con el ID generado por la base de datos.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public Usuario agregarUsuario(Usuario usuario) throws PersistenciaException {
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO usuarios (correo, cedulaProfesional, contrasenia, tipo)VALUES (?, ?, ?, ?)";

        //Encriptamos la contraseña antes de guardarla
        String contraseniaEncriptada = BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt());

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = 
                con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros de la consulta
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getCedulaProfesional());
            ps.setString(3, contraseniaEncriptada);
            ps.setObject(4, usuario.getTipo().toString(), Types.VARCHAR);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                logger.severe("Error: No se pudo crear el usuario.");
                throw new PersistenciaException("Error: No se pudo crear el usuario.");
            }
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setIdUsuario(generatedKeys.getInt(1));
                    logger.info("Usuario creado con ID: " + usuario.getIdUsuario());
                } else {
                    logger.severe("Error: No se obtuvo el ID del usuario creado.");
                    throw new PersistenciaException("Error: No se obtuvo el ID del usuario creado.");
                }
            }
            return usuario;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear usuario: " + e.getMessage(), e);
            throw new PersistenciaException("Error al crear usuario: " + e.getMessage(), e);
        }
    }
    
    /**
     * Inicia sesión de un usuario, verificando el correo o cédula y la contraseña encriptada.
     *
     * @param identificador Correo electrónico o cédula profesional del usuario.
     * @param contrasenia Contraseña del usuario.
     * @return Objeto Usuario con los datos del usuario si la autenticación es exitosa.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos o la autenticación falla.
     */
    @Override
    public Usuario iniciarSesion(String identificador, String contrasenia) throws PersistenciaException {
        Usuario usuario;
        try {
            usuario = obtenerUsuario(identificador);
            if(usuario == null){
                logger.warning("Usuario no encontrado con identificador: " + identificador);
                throw new PersistenciaException("No hay registros de un usuario con este correo o cedula");
            }
            
            String contraseniaEncriptada = usuario.getContrasenia();
            if (BCrypt.checkpw(contrasenia, contraseniaEncriptada)) {
                return usuario;
            } else {
                logger.warning("Contraseña incorrecta para el usuario: " + identificador);
                throw new PersistenciaException("Error: la contraseña no coincide con el correo o cedula");
            }
        } catch (PersistenciaException e) {
            throw e; // Relanzar excepciones de persistencia sin envolver
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al iniciar sesión: " + e.getMessage(), e);
            throw new PersistenciaException("Error al iniciar sesión: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un usuario por su correo electrónico o cédula profesional.
     *
     * @param entrada Correo electrónico o cédula profesional del usuario a obtener.
     * @return Objeto Usuario con los datos del usuario, o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public Usuario obtenerUsuario(String entrada) throws PersistenciaException {
        // auxiliar de usuario
        Usuario usuario = null;
        String tipo;
        String consultaSQL = "SELECT id, correo, cedulaProfesional, contrasenia, tipo FROM usuarios WHERE correo = ? OR cedulaProfesional = ?";
        
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setString(1, entrada);
            ps.setString(2, entrada);

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
                    logger.warning("Usuario no encontrado con entrada: " + entrada);
                    throw new PersistenciaException("Usuario no encontrado.");
                }
            }
            return usuario;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar usuario con correo o cedula: " + entrada, e);
            throw new PersistenciaException("Error al consultar usuario por correo o cedula " + entrada, e);

        }
    }
    
    /**
     * Obtiene un usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a obtener.
     * @return Objeto Usuario con los datos del usuario, o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
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
            return usuario;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar usuario: " + e.getMessage(), e);
            throw new PersistenciaException("Error al consultar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un usuario por su cédula profesional.
     *
     * @param cedula Cédula profesional del usuario a obtener.
     * @return Objeto Usuario con los datos del usuario, o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
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
    
    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return Lista de objetos Usuario con todos los usuarios.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public List<Usuario> obtenerUsuarios() throws PersistenciaException {
        String consultaSQL = "SELECT id, correo, cedulaProfesional, contrasenia, tipo FROM usuarios";

        // Lista donde se almacenarán los usuarios recuperados
        List<Usuario> usuarios = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL); 
                ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
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
    
    /**
     * Encripta todas las contraseñas de los usuarios en la base de datos.
     *
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public void encriptarContrasenias() throws PersistenciaException {
        String sqlSelect = "SELECT id, contrasenia FROM usuarios";
        String sqlUpdate = "UPDATE usuarios SET contrasenia = ? WHERE id = ?";
        
        try (Connection con = this.conexion.crearConexion();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);
            Statement stmtSelect = con.createStatement();
            ResultSet rs = stmtSelect.executeQuery(sqlSelect)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String contrasenia = rs.getString("contrasenia");
                String contraseniaEncriptada = BCrypt.hashpw(contrasenia, BCrypt.gensalt());

                ps.setString(1, contraseniaEncriptada);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }catch(Exception e){
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error a encriptar las contraseñas.", e);
        }
    }
}
