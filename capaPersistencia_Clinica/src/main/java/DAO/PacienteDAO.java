/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import java.sql.Connection;
import java.sql.Date;
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
    private final PacienteMapper mapper = new PacienteMapper();
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
            
             int filasAfectadas = ps.executeUpdate(); 
            if (filasAfectadas == 0) { 
                logger.severe("La creación del paciente falló");
                throw new PersistenciaException("La creación del paciente falló");
            }          
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) { 
                if (generatedKeys.next()) { 
                    paciente.setIdPaciente(generatedKeys.getInt(1)); 
                    logger.info("Activista creado exitosamente con ID: " + paciente.getIdPaciente()); 
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
    
    //ACTUALIZAR PACIENTE
    public Paciente actualizarPaciente(Paciente paciented)throws PersistenciaException{
        String consultaSQL = "UPDATE pacientes SET nombre = ?, apellidoPat=?, apellidoMat=?, fechaNacimiento=?, calle=?, colonia=?, numero=?, telefono=?, correo=? WHERE id = ?;";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos los parámetros correctamente
            ps.setString(1, paciented.getNombre());
            ps.setString(2, paciented.getApellidoPaterno());
            ps.setString(3, paciented.getApellidoMaterno());
            ps.setObject(4, paciented.getFechaNacimiento());
            ps.setString(5, paciented.getCalle());
            ps.setString(6, paciented.getColonia());
            ps.setString(7, paciented.getNumero());
            ps.setString(8, paciented.getTelefono());
            ps.setString(9, paciented.getCorreo());
            ps.setInt(10, paciented.getIdPaciente());// ID del paciente

            // Ejecutamos la actualización
            int filasActualizadas = ps.executeUpdate();
            PacienteNuevoDTO paciente = mapper.toNuevoDTO(paciented);
            if (filasActualizadas > 0) {
                logger.info("Paciente actualizado correctamente: " + paciente);

            } else {
                logger.warning("No se encontró paciente con ID: " + paciented.getIdPaciente());

            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + paciented.getIdPaciente(), e);
            throw new PersistenciaException("Error al actualizar paciente con ID " + paciented.getIdPaciente(), e);
        }
        return paciented;
    }
}
