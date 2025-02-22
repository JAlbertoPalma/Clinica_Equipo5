/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class MedicoDAO implements IMedicoDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(MedicoDAO.class.getName());

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    //Dar de baja al medico
    @Override
    public boolean darBajaMedico(int idMedico) throws PersistenciaException {
        try (Connection con = this.conexion.crearConexion(); CallableStatement pstmt = con.prepareCall("call dar_baja_medico (?)")) {
            pstmt.setInt(1, idMedico);  // Establecer el parámetro del médico a eliminar
            int filasAfectadas = pstmt.executeUpdate();  // Ejecutar el procedimiento almacenado
            if (filasAfectadas > 0) {
                return true;  // Si se afectaron filas, significa que la baja fue exitosa
            } else {
                throw new PersistenciaException("No se pudo dar de baja el médico con ID: " + idMedico);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al dar de baja médico.", e);
        }
    }

    //Consultar de historial del medico
    @Override
    public List<Map<String, Object>> consultarHistorialConsultas(int idMedico) throws PersistenciaException {   //Funciona
        List<Map<String, Object>> agenda = new ArrayList<>();
        try (Connection con = this.conexion.crearConexion(); ) {
            CallableStatement pstmt = con.prepareCall("SELECT id_consulta, nombre_paciente, apellidoPat_paciente, apellidoMat_paciente, fechaHora, tipo, estado, tratamiento, diagnostico FROM vista_historial_consultas_medico WHERE id_medico = ?");     
            pstmt.setInt(1, idMedico);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> consulta = new HashMap<>();
                consulta.put("id_consulta", rs.getInt("id_consulta"));
                consulta.put("nombre_paciente", rs.getString("nombre_paciente"));
                consulta.put("apellido_paterno", rs.getString("apellidoPat_paciente"));
                consulta.put("apellido_materno", rs.getString("apellidoMat_paciente"));
                consulta.put("fechaHora", rs.getTimestamp("fechaHora").toLocalDateTime());
                consulta.put("tipo", rs.getString("tipo"));
                consulta.put("estado", rs.getString("estado"));
                consulta.put("tratamiento", rs.getString("tratamiento"));
                consulta.put("diagnostico", rs.getString("diagnostico"));
                agenda.add(consulta);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar la agenda del médico.", e);
        }
        return agenda;
    }

    //Consultar agenda del medico
    @Override
    public List<Map<String, Object>> consultarAgenda(int idMedico) throws PersistenciaException {
        List<Map<String, Object>> agenda = new ArrayList<>();
        try (Connection con = this.conexion.crearConexion(); ) {
            CallableStatement pstmt = con.prepareCall("SELECT id_cita, nombre_paciente, apellido_paciente, horaInicio, horaFin, estado FROM vista_agenda_medico WHERE id_medico = ?");
            pstmt.setInt(1, idMedico);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> cita = new HashMap<>();
                cita.put("id_cita", rs.getInt("id_cita"));
                cita.put("nombre_paciente", rs.getString("nombre_paciente"));
                cita.put("apellido_paciente", rs.getString("apellido_paciente"));
                cita.put("horaInicio", rs.getTimestamp("horaInicio").toLocalDateTime());
                cita.put("horaFin", rs.getTimestamp("horaFin").toLocalDateTime());
                cita.put("estado", rs.getString("estado"));
                agenda.add(cita);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar la agenda del médico.", e);
        }
        return agenda;
    }
}
