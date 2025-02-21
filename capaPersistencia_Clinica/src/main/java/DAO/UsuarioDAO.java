/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Usuario agregarUsuario(Usuario usuario)throws PersistenciaException{
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO usuarios (correo, cedula_profesional, contraseña, tipo)VALUES (?, ?, ?, ?)";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros de la consulta
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getCedulaProfesional());
            ps.setString(3, usuario.getContrasenia());
            ps.setObject(4, usuario.getTipo());

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
       } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear usuario", e);
            throw new PersistenciaException("Error al crear al usuario", e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
