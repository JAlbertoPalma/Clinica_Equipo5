/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
import Exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class CitaDAO implements ICitaDAO{
    
    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(CitaDAO.class.getName());

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    @Override
    public Cita agendarCita(Cita cita) throws PersistenciaException {
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO citas (fecha, horaInicio, horaFin, estado, id_medico, id_paciente)VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.crearConexion(); 
                PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros de la consulta
            ps.setObject(1, cita.getFecha(), Types.DATE);
            ps.setObject(2, cita.getHoraInicio(), Types.TIME);
            ps.setObject(3, cita.getHoraFin(), Types.TIME);
            ps.setObject(4, cita.getEstado().toString(), Types.VARCHAR);
            ps.setInt(5, cita.getIdMedico());
            ps.setInt(6, cita.getIdPaciente());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                logger.severe("La creación de la cita falló");
                throw new PersistenciaException("La creación del usuario falló");
            }
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cita.setIdCita(generatedKeys.getInt(1));
                    logger.info("Cita creada con fecha: " + cita.getFecha() + " y hora: " + cita.getHoraInicio());
                } else {
                    logger.severe("La creación de la cita falló, no se obtuvo ID.");
                    throw new PersistenciaException("La creación de la cita falló, no se obtuvo ID.");
                }
            }
            return cita;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear cita", e.getMessage());
            throw new PersistenciaException("Error al crear al cita: " + e.getMessage());
        }
    }

    @Override
    public boolean cancelarCita(int idCita) throws PersistenciaException {
        String sentenciaSQL = "UPDATE citas SET estado = 'cancelada' WHERE id = ?";
        
        try (Connection con = this.conexion.crearConexion(); 
                PreparedStatement ps = con.prepareStatement(sentenciaSQL)) {
            // Asignamos los parámetros correctamente
            ps.setInt(1, idCita); // WHERE id = ?
            // Ejecutamos la actualización
            int filasAfectadas = ps.executeUpdate();
            logger.info("Cancelada la cita con id: " + idCita);
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cancelar la cita con ID: " + idCita, e);
            throw new PersistenciaException("Error al cancelar la cita con ID: " + idCita, e.getCause());
        }
    }
    
    @Override
    public Cita obtenerCita(int idCita) throws PersistenciaException{
        // auxiliar de usuario
        Cita cita = null;
        
        
        String consultaSQL = "SELECT id, fecha, horaInicio, horaFin, estado, id_medico, id_paciente FROM citas WHERE id = ?";
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setInt(1, idCita);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto cita y se asignan sus propiedades
                    cita = new Cita();
                    String estado = rs.getString("estado");
                    
                    cita.setIdCita(rs.getInt("id"));
                    cita.setFecha(rs.getObject("fecha", LocalDate.class));
                    cita.setHoraFin(rs.getObject("horaInicio", LocalTime.class));
                    cita.setHoraInicio(rs.getObject("horaFin", LocalTime.class));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setIdMedico(rs.getInt("id_paciente"));
                    
                    switch (estado) {
                    case "atendida" -> cita.setEstado(Cita.EstadoCita.atendida);
                    case "cancelada" -> cita.setEstado(Cita.EstadoCita.cancelada);
                    case "pendiente" -> cita.setEstado(Cita.EstadoCita.pendiente);
                    default -> {
                    }
                }
                    
                    logger.info("Cita encontrada: " + cita);
                } else {
                    logger.warning("No se encontró la cita con id: " + idCita); // no es error, solo advertencia
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar cita con id: " + idCita, e);
            throw new PersistenciaException("La cita con id: '" + idCita + "' no existe", e);

        }
        return cita;
    }

    @Override
    public List<Cita> obtenerTodas() throws PersistenciaException {
        String consultaSQL = "SELECT id, fecha, horaInicio, horaFin, estado, id_medico, id_paciente FROM citas WHERE id";

        // Lista donde se almacenarán los usuarios recuperados
        List<Cita> citas = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL);
                ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
                ) {
            // Se recorre el ResultSet mientras haya filas disponibles con el next()
            while (rs.next()) {
                
                // Se crea el objeto medico y se asignan sus propiedades
                    // Se crea el objeto cita y se asignan sus propiedades
                    Cita cita = new Cita();
                    String estado = rs.getString("estado");
                    
                    cita.setIdCita(rs.getInt("id"));
                    cita.setFecha(rs.getObject("fecha", LocalDate.class));
                    cita.setHoraFin(rs.getObject("horaInicio", LocalTime.class));
                    cita.setHoraInicio(rs.getObject("horaFin", LocalTime.class));
                    cita.setIdMedico(rs.getInt("id_medico"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    
                    switch (estado) {
                        case "atendida" -> cita.setEstado(Cita.EstadoCita.atendida);
                        case "cancelada" -> cita.setEstado(Cita.EstadoCita.cancelada);
                        case "pendiente" -> cita.setEstado(Cita.EstadoCita.pendiente);
                        default -> {
                        }
                    }
                    
                    logger.info("Cita encontrada: " + cita);

                // Se agrega el usuario a la lista
                citas.add(cita);
            }

            // Se retorna la lista con todos los usuarios obtenidos
            return citas;

        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error al obtener la lista de medicos.", ex);
        }
    }
    
}
