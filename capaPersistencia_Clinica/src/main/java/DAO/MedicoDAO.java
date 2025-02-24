/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Medico;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
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
    public void darBajaMedico(int idMedico) throws PersistenciaException {
        try (Connection con = this.conexion.crearConexion(); CallableStatement pstmt = con.prepareCall("call dar_baja_medico (?)")) {
            pstmt.setInt(1, idMedico);  
            int filasAfectadas = pstmt.executeUpdate();  
            
//            if (filasAfectadas > 0) {
//                return true; 
//            } else {
//                throw new PersistenciaException("No se pudo dar de baja el médico con ID: " + idMedico);
//            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo dar de baja el médico con ID: " + idMedico, e);
        }
    }
    
    @Override
    public Medico obtenerMedico(int idMedico) throws PersistenciaException{
        // auxiliar de usuario
        Medico medico = null;
        String tipo;
        
        
        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, especialidad, cedulaProfesional, estaActivo, id_usuario FROM medicos WHERE id = ?";
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setInt(1, idMedico);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto medico y se asignan sus propiedades
                    medico = new Medico();
                    
                    medico.setIdMedico(rs.getInt("id"));
                    medico.setNombre(rs.getString("nombre"));
                    medico.setApellidoPaterno(rs.getString("apellidoPat"));
                    medico.setApellidoMaterno(rs.getString("apellidoMat"));
                    medico.setCedulaProfesional(rs.getString("cedulaProfesional"));
                    medico.setEstaActivo(rs.getBoolean("estaActivo"));
                    medico.setIdUsuario(rs.getInt("id_usuario"));
                    
                    
                    tipo = rs.getString("especialidad");
                    if(tipo.equals("cardiologia")){
                        medico.setEspecialidad(Medico.EspecialidadMedico.cardiologia);
                    }else if(tipo.equals("oftamologia")){
                        medico.setEspecialidad(Medico.EspecialidadMedico.oftamologia);
                    }else if(tipo.equals("ortopedia")){
                        medico.setEspecialidad(Medico.EspecialidadMedico.ortopedia);
                    }else if(tipo.equals("neurologia")){
                        medico.setEspecialidad(Medico.EspecialidadMedico.neurologia);
                    }else if(tipo.equals("nefrologia")){
                        medico.setEspecialidad(Medico.EspecialidadMedico.nefrologia);
                    }
                    
                    logger.info("Medico encontrado: " + medico);
                } else {
                    logger.warning("No se encontró el médico con id: " + idMedico); // no es error, solo advertencia
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar medico con id: " + idMedico, e);
            throw new PersistenciaException("Error al consultar medico por id " + idMedico, e);

        }
        return medico;
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
    public List<Map<String, Object>> consultarAgenda(int idMedico) throws PersistenciaException {//Funciona
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
                cita.put("horaInicio", rs.getObject(("horaInicio"), LocalTime.class));
                cita.put("horaFin", rs.getObject(("horaFin"), LocalTime.class));
                cita.put("estado", rs.getString("estado"));
                agenda.add(cita);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar la agenda del médico.", e);
        }
        return agenda;
    }
}
