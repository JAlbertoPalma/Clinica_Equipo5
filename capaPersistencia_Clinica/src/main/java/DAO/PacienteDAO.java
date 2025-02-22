/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        String sentenciaSQL = "INSERT INTO pacientes (nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo, id_usuario)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setInt(10, paciente.getIdUsuario());

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
    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException {
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
            ps.setInt(10, paciente.getIdPaciente()); // WHERE id = ?

            // Ejecutamos la actualización
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + paciente.getIdPaciente(), e);
            throw new PersistenciaException("Error al actualizar paciente con ID " + paciente.getIdPaciente(), e);
        }
    }

    //Consulta historial de consultas del paciente
    @Override
    public List<Map<String, Object>> consultarHistorialConsultas(int idPaciente, String tipoConsulta, LocalDate fechaInicio, LocalDate fechaFin) throws PersistenciaException {
        List<Map<String, Object>> historial = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id_consulta, nombre_medico, apellidoPat_medico, apellidoMat_medico, fechaHora, tipo, estado, tratamiento, diagnostico FROM vista_historial_consultas_paciente WHERE id_paciente = ?");
        if (tipoConsulta != null && !tipoConsulta.isEmpty()) {
            sql.append(" AND tipo = ?");
        }
        if (fechaInicio != null && fechaFin != null) {
            sql.append(" AND DATE(fechaHora) BETWEEN ? AND ?");
        }
        try (Connection con = this.conexion.crearConexion(); PreparedStatement pstmt = con.prepareStatement(sql.toString())) {

            int index = 1;
            pstmt.setInt(index++, idPaciente);

            if (tipoConsulta != null && !tipoConsulta.isEmpty()) {
                pstmt.setString(index++, tipoConsulta);
            }
            if (fechaInicio != null && fechaFin != null) {
                pstmt.setDate(index++, Date.valueOf(fechaInicio));
                pstmt.setDate(index++, Date.valueOf(fechaFin));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> consulta = new HashMap<>();
                consulta.put("id_consulta", rs.getInt("id_consulta"));
                consulta.put("nombre_medico", rs.getString("nombre_medico"));
                consulta.put("apellidoPat_medico", rs.getString("apellidoPat_medico"));
                consulta.put("apellidoMat_medico", rs.getString("apellidoMat_medico"));
                consulta.put("fechaHora", rs.getTimestamp("fechaHora").toLocalDateTime());
                consulta.put("tipo", rs.getString("tipo"));
                consulta.put("estado", rs.getString("estado"));
                consulta.put("tratamiento", rs.getString("tratamiento"));
                consulta.put("diagnostico", rs.getString("diagnostico"));

                historial.add(consulta);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el historial de consultas del paciente.", e);
        }
        return historial;
    }
}
