/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.ConsultaUrgencia;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class PacienteBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper(); // Usamos el mapper

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public boolean agregarPaciente(PacienteNuevoDTO pacienteDTO) throws NegocioException {  //Funciona
        if (pacienteDTO == null) {
            throw new NegocioException("El paciente no puede ser nulo.");
        }
        //validaciones de espacios vacíos
        if (pacienteDTO.getNombre().isEmpty() || pacienteDTO.getApellidoPaterno().isEmpty()
                || pacienteDTO.getCalle().isEmpty() || pacienteDTO.getColonia().isEmpty()
                || pacienteDTO.getNumero().isEmpty() || pacienteDTO.getTelefono().isEmpty()
                || pacienteDTO.getCorreo().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }

        // Convertimos el DTO a la entidad
        Paciente paciente = mapper.toEntity(pacienteDTO);

        try {
            Paciente pacienteGuardado = pacienteDAO.agregarPaciente(paciente);
            return pacienteGuardado != null;
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al guardar paciente en la Base de Datos", ex);
            throw new NegocioException("Hubo un error al guardar el paciente.", ex);
        }
    }

    //Actualizacion de paciente
    public Paciente actualizarPaciente(PacienteViejoDTO pacienteDTO) throws NegocioException { //Funciona
        //validaciones de espacios vacíos
        if (pacienteDTO.getNombre().isEmpty() || pacienteDTO.getApellidoPaterno().isEmpty()
                || pacienteDTO.getCalle().isEmpty() || pacienteDTO.getColonia().isEmpty()
                || pacienteDTO.getNumero().isEmpty() || pacienteDTO.getTelefono().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }

        // Obtener el ID del paciente desde el DTO (asumiendo que pacienteViejoDTO contiene el idPaciente)
        int idPaciente = pacienteDTO.getId_paciente();
        System.out.println("Id en la BO es: "+pacienteDTO.getId_paciente());
        // Mapeo del DTO a la entidad
        Paciente paciente = mapper.toEntityV(pacienteDTO);
        paciente.setIdPaciente(idPaciente);

        try {
            // Llamar a la DAO para actualizar el paciente
            pacienteDAO.actualizarPaciente(paciente);

        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + idPaciente, ex);
            throw new NegocioException("No se pudo actualizar el paciente.", ex);
        }
        return paciente;
    }

    public PacienteViejoDTO obtenerPaciente(int idPaciente) throws NegocioException {
        try {
            return mapper.toViejoDTO(pacienteDAO.obtenerPaciente(idPaciente));
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se logró obtener al paciente" + ex.getMessage());
        }
    }

    public List<PacienteViejoDTO> obtenerTodos() throws NegocioException {
        try {
            return mapper.toViejoDTOList(pacienteDAO.obtenerPacientes());
        } catch (PersistenciaException pe) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, pe);
            throw new NegocioException("No se lograron obtener los pacientes" + pe.getMessage());
        }
    }

    //Consulta historial de consultas del paciente
    public List<Map<String, Object>> consultarHistorialConsultasPaciente(int idPaciente, String tipoConsulta, LocalDate fechaInicio, LocalDate fechaFin) throws NegocioException {//Funciona
        if (idPaciente <= 0) {
            throw new NegocioException("El ID del paciente debe ser válido.");
        }
        if ((fechaInicio != null && fechaFin != null) && fechaInicio.isAfter(fechaFin)) {
            throw new NegocioException("La fecha de inicio no puede ser mayor que la fecha de fin.");
        }
        try {
            return pacienteDAO.consultarHistorialConsultas(idPaciente, tipoConsulta, fechaInicio, fechaFin);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al consultar el historial de consultas del paciente con ID: " + idPaciente, e);
            throw new NegocioException("No se pudo obtener el historial de consultas del paciente.", e);
        }
    }

    //Buscar citas disponibles
    public List<Map<String, Object>> consultarCitasDisponibles(int idMedico, String fechaCita) throws NegocioException {//Funciona
        if (idMedico <= 0) {
            throw new NegocioException("El ID del paciente debe ser válido.");
        }
        if (fechaCita == null || fechaCita.isEmpty()) {
            throw new NegocioException("La fecha de la cita no puede estar vacía.");
        }
        try {
            return pacienteDAO.buscarCitasDisponibles(idMedico, fechaCita);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar citas disponibles: " + e.getMessage(), e);
        }
    }

    //AsignarMedicoUrgencia
    public ConsultaUrgencia asignarMedicoUrgencia(int idPaciente) throws NegocioException {
        try {
            if (idPaciente <= 0) {
                throw new NegocioException("El ID del paciente debe ser válido.");
            }
            return pacienteDAO.asignarMedicoUrgencia(idPaciente);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error: " + pe.getMessage());
        }
    }
    
    //Encontrar id del paciente con correo
    public int EncontraridPaciente(String correo)throws NegocioException{
        try{
            if (correo==null) {
                throw new NegocioException("El ID del paciente debe ser válido.");
            }
            return pacienteDAO.EncontraridPaciente(correo);
        }catch(PersistenciaException pe){
            throw new NegocioException("Error: " + pe.getMessage());
        }
    }

}
