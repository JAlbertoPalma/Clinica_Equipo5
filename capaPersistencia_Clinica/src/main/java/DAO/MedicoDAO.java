/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Medico;
import Entidades.Medico.EspecialidadMedico;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
            pstmt.executeUpdate(); 
            
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo dar de baja el médico con ID: " + idMedico, e);
        }
    }
    
    @Override
    public boolean actualizarMedico(int idMedico, Medico medico) throws PersistenciaException{
        String consultaSQL = "UPDATE medicos SET nombre = ?, apellidoPat=?, apellidoMat=?, especialidad=?, cedulaProfesional=?, id_usuario=? WHERE id = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {
            // Asignamos los parámetros correctamente
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellidoPaterno());
            ps.setString(3, medico.getApellidoMaterno());
            ps.setObject(4, medico.getEspecialidad().toString(), Types.VARCHAR);
            ps.setString(5, medico.getCedulaProfesional());
            ps.setInt(6, medico.getIdUsuario());
            ps.setInt(7, idMedico);
            
            // Ejecutamos la actualización
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar medico con ID: " + idMedico, e);
            throw new PersistenciaException("Error al actualizar medico con ID " + idMedico, e);
        }
    }
    
    @Override
    public Medico obtenerMedico(int idMedico) throws PersistenciaException{
        // auxiliar de usuario
        Medico medico = null;
        String especialidad;
        
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
                    
                    
                    especialidad = rs.getString("especialidad");
                    switch (especialidad) {
                        case "cardiologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.cardiologia);
                        case "oftamologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.oftamologia);
                        case "ortopedia" -> medico.setEspecialidad(Medico.EspecialidadMedico.ortopedia);
                        case "neurologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.neurologia);
                        case "nefrologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.nefrologia);
                        default -> {
                        }
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
    
    @Override
    public Medico obtenerMedicoPorCedula(String cedula) throws PersistenciaException{
        // auxiliar de usuario
        Medico medico = null;
        String especialidad;
        
        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, especialidad, cedulaProfesional, estaActivo, id_usuario FROM medicos WHERE cedulaProfesional = ?";
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setString(1, cedula);

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
                    
                    
                    especialidad = rs.getString("especialidad");
                    switch (especialidad) {
                        case "cardiologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.cardiologia);
                        case "oftamologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.oftamologia);
                        case "ortopedia" -> medico.setEspecialidad(Medico.EspecialidadMedico.ortopedia);
                        case "neurologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.neurologia);
                        case "nefrologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.nefrologia);
                        default -> {
                        }
                    }
                    
                    logger.info("Medico encontrado: " + medico);
                } else {
                    logger.warning("No se encontró el médico con cedula: " + cedula); // no es error, solo advertencia
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar medico con cedula: " + cedula, e);
            throw new PersistenciaException("Error al consultar medico por cedula " + cedula, e);

        }
        return medico;
    }

    
    @Override
    public List<Medico> obtenerMedicosActivos() throws PersistenciaException{
        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, especialidad, cedulaProfesional, estaActivo, id_usuario FROM medicos WHERE estaActivo = TRUE";

        // Lista donde se almacenarán los usuarios recuperados
        List<Medico> medicos = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL);
                ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
                ) {
            // Se recorre el ResultSet mientras haya filas disponibles con el next()
            while (rs.next()) {
                
                // Se crea el objeto medico y se asignan sus propiedades
                    Medico medico = new Medico();
                    String especialidad = rs.getString("especialidad");
                    
                    medico.setIdMedico(rs.getInt("id"));
                    medico.setNombre(rs.getString("nombre"));
                    medico.setApellidoPaterno(rs.getString("apellidoPat"));
                    medico.setApellidoMaterno(rs.getString("apellidoMat"));
                    medico.setCedulaProfesional(rs.getString("cedulaProfesional"));
                    medico.setEstaActivo(rs.getBoolean("estaActivo"));
                    medico.setIdUsuario(rs.getInt("id_usuario"));
                    
                switch (especialidad) {
                    case "cardiologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.cardiologia);
                    case "oftamologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.oftamologia);
                    case "ortopedia" -> medico.setEspecialidad(Medico.EspecialidadMedico.ortopedia);
                    case "neurologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.neurologia);
                    case "nefrologia" -> medico.setEspecialidad(Medico.EspecialidadMedico.nefrologia);
                    default -> {
                    }
                }

                // Se agrega el usuario a la lista
                medicos.add(medico);
            }

            // Se retorna la lista con todos los usuarios obtenidos
            return medicos;

        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error al obtener la lista de medicos.", ex);
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
