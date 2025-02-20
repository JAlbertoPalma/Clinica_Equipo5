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
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    //agregar Paciente
    @Override
    public Paciente agregarPaciente(Paciente paciente) throws PersistenciaException {
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO pacientes (nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

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
                    logger.info("Paciente creado exitosamente con ID: " + paciente.getIdPaciente());
                } else {
                    logger.severe("La creación del paciente falló, no se obtuvo ID.");
                    throw new PersistenciaException("La creación del paciente falló, no se obtuvo ID.");
                }
            }
            return paciente;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear paciente", e);
            throw new PersistenciaException("Error al crear al paciente", e);
        }
    }

    //ACTUALIZAR PACIENTE
    @Override
    public Paciente actualizarPaciente(Paciente paciente) throws PersistenciaException {
        String consultaSQL = "UPDATE pacientes SET nombre = ?, apellidoPat=?, apellidoMat=?, fechaNacimiento=?, calle=?, colonia=?, numero=?, telefono=?, correo=? WHERE id = ?;";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos los parámetros correctamente
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setObject(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getCalle());
            ps.setString(6, paciente.getColonia());
            ps.setString(7, paciente.getNumero());
            ps.setString(8, paciente.getTelefono());
            ps.setString(9, paciente.getCorreo());
            ps.setInt(10, paciente.getIdPaciente()); // ID del paciente

            // Ejecutamos la actualización
            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas > 0) {
                logger.info("Paciente actualizado correctamente: " + paciente);
                return paciente; // Retornar el mismo objeto modificado
            } else {
                logger.warning("No se encontró paciente con ID: " + paciente.getIdPaciente());
                return null; // Indicar que no se actualizó ningún registro
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + paciente.getIdPaciente(), e);
            throw new PersistenciaException("Error al actualizar paciente con ID " + paciente.getIdPaciente(), e);
        }
    }
}
