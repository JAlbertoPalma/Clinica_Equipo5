/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class PacienteDAO implements IPacienteDAO{
    IConexionBD conexion; 
    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());
    
    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    //agregar Paciente
    @Override
    public Paciente agregarPaciente(Paciente paciente)throws PersistenciaException{
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO pacientes (nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            // Se establecen los parámetros de la consulta
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setObject(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getCalle());
            ps.setString(6, paciente.getColonia());
            ps.setString(7, paciente.getNumero());
            ps.setString(8, paciente.getTelefono());
            ps.setString(9, paciente.getCorreo());
            
             int filasAfectadas = ps.executeUpdate(); // Usa executeUpdate() si vas a modificar datos (INSERT, UPDATE, DELETE).
            if (filasAfectadas == 0) { // comprobamos con 0, porque si no se inserto nada nos regresaria 0 registros modificados
                logger.severe("La creación del paciente falló");
                throw new PersistenciaException("La creación del paciente falló");
            }          
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) { 
                if (generatedKeys.next()) { 
                    paciente.setIdPaciente(generatedKeys.getInt(1)); // asignamos el id que obtuvimos al paciente que recibimos en el parametro
                    logger.info("Activista creado exitosamente con ID: " + paciente.getIdPaciente()); // mensaje de confirmación 
                } else {
                    logger.severe("La creación del activista falló, no se obtuvo ID."); 
                    throw new PersistenciaException("La creación del activista falló, no se obtuvo ID.");
                }
            }
            return paciente;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear paciente", e);
            throw new PersistenciaException("Error al crear al paciente", e);
        }      
    }
}
