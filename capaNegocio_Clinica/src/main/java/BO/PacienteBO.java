/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.ConsultaUrgenciaDTO;
import DTO.PacienteNuevoDTO;
import DTO.PacienteViejoDTO;
import Entidades.ConsultaUrgencia;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.CitaMapper;
import Mapper.PacienteMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de Lógica de Negocio para la gestión de pacientes.
 * @author pablo
 */
public class PacienteBO {

    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper mapper = new PacienteMapper(); // Usamos el mapper
    private final CitaMapper mapperCita = new CitaMapper(); // Usamos el mapper

    /**
     * Constructor de PacienteBO.
     * Inicializa el objeto PacienteDAO con la conexión a la base de datos proporcionada.
     *
     * @param conexion Objeto IConexionBD para la conexión a la base de datos.
     */
    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    /**
     * Agrega un nuevo paciente a la base de datos.
     *
     * @param pacienteDTO DTO con los datos del paciente a agregar.
     * @return true si la adición fue exitosa, false en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso de adición.
     */
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

    /**
     * Actualiza los datos de un paciente.
     *
     * @param idPaciente ID del paciente a actualizar.
     * @param pacienteDTO DTO con los nuevos datos del paciente.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso de actualización.
     */
    public boolean actualizarPaciente(int idPaciente, PacienteNuevoDTO pacienteDTO) throws NegocioException { //Funciona
        //validaciones de espacios vacíos
        if (pacienteDTO.getNombre().isEmpty() || pacienteDTO.getApellidoPaterno().isEmpty()
                || pacienteDTO.getCalle().isEmpty() || pacienteDTO.getColonia().isEmpty()
                || pacienteDTO.getNumero().isEmpty() || pacienteDTO.getTelefono().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }

        System.out.println("Id en la BO es: "+idPaciente);
        // Mapeo del DTO a la entidad
        Paciente paciente = mapper.toEntity(pacienteDTO);

        try {
            // Llamar a la DAO para actualizar el paciente
            pacienteDAO.actualizarPaciente(idPaciente, paciente);

        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + idPaciente, ex);
            throw new NegocioException("No se pudo actualizar el paciente.", ex);
        }
        return true;
    }

    /**
     * Obtiene los datos de un paciente por su ID.
     *
     * @param idPaciente ID del paciente a obtener.
     * @return DTO con los datos del paciente.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public PacienteViejoDTO obtenerPaciente(int idPaciente) throws NegocioException {
        try {
            return mapper.toViejoDTO(pacienteDAO.obtenerPaciente(idPaciente));
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se logró obtener al paciente" + ex.getMessage());
        }
    }
    
    /**
     * Obtiene los datos de un paciente por su correo electrónico.
     *
     * @param correo Correo electrónico del paciente a obtener.
     * @return DTO con los datos del paciente.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public PacienteViejoDTO obtenerPacientePorCorreo(String correo) throws NegocioException {
        try {
            return mapper.toViejoDTO(pacienteDAO.obtenerPacientePorCorreo(correo));
        } catch (PersistenciaException ex) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se logró obtener al paciente" + ex.getMessage());
        }
    }

    /**
     * Obtiene una lista con todos los pacientes.
     *
     * @return Lista de DTOs con los datos de todos los pacientes.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public List<PacienteViejoDTO> obtenerTodos() throws NegocioException {
        try {
            return mapper.toViejoDTOList(pacienteDAO.obtenerPacientes());
        } catch (PersistenciaException pe) {
            Logger.getLogger(PacienteBO.class.getName()).log(Level.SEVERE, null, pe);
            throw new NegocioException("No se lograron obtener los pacientes" + pe.getMessage());
        }
    }

    /**
     * Consulta el historial de consultas de un paciente.
     *
     * @param idPaciente ID del paciente para consultar el historial.
     * @param tipoConsulta Tipo de consulta a filtrar (opcional).
     * @param fechaInicio Fecha de inicio para filtrar por rango de fechas (opcional).
     * @param fechaFin Fecha de fin para filtrar por rango de fechas (opcional).
     * @return Lista de mapas con los datos del historial de consultas.
     * @throws NegocioException Si ocurre un error durante el proceso de consulta.
     */
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

    /**
     * Busca citas disponibles para un médico en una fecha específica.
     *
     * @param idMedico ID del médico para buscar citas disponibles.
     * @param fechaCita Fecha de la cita para buscar horarios disponibles.
     * @return Lista de mapas con los horarios disponibles.
     * @throws NegocioException Si ocurre un error durante el proceso de búsqueda.
     */
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

    /**
     * Asigna un médico de urgencia a un paciente.
     *
     * @param idPaciente ID del paciente para asignar un médico de urgencia.
     * @return DTO con los datos de la asignación.
     * @throws NegocioException Si ocurre un error durante el proceso de asignación.
     */
    public ConsultaUrgenciaDTO asignarMedicoUrgencia(int idPaciente) throws NegocioException {
        try {
            if (idPaciente <= 0) {
                throw new NegocioException("El ID del paciente debe ser válido.");
            }
            return mapperCita.toUrgenciaDTO(pacienteDAO.asignarMedicoUrgencia(idPaciente));
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error: " + pe.getMessage());
        }
    }
    
    /**
     * Consulta las citas de un paciente, filtradas por especialidad y rango de fechas.
     *
     * @param idPaciente ID del paciente para consultar sus citas.
     * @param especialidad Especialidad para filtrar las citas (opcional).
     * @param fechaInicio Fecha de inicio para filtrar por rango de fechas (opcional).
     * @param fechaFin Fecha de fin para filtrar por rango de fechas (opcional).
     * @return Lista de mapas con los datos de las citas del paciente.
     * @throws NegocioException Si ocurre un error durante el proceso de consulta.
     */
    public List<Map<String, Object>> consultarCitasPaciente(int idPaciente, String especialidad, LocalDate fechaInicio, LocalDate fechaFin) throws NegocioException{
        try{
            return pacienteDAO.consultarCitasPaciente(idPaciente, especialidad, fechaInicio, fechaFin);
        } catch (PersistenciaException pe) {
            throw new NegocioException("Error: " + pe.getMessage());
        }
    }
    
    /**
     * Encuentra el ID de un paciente por su correo electrónico.
     *
     * @param correo Correo electrónico del paciente para buscar su ID.
     * @return ID del paciente encontrado.
     * @throws NegocioException Si ocurre un error durante el proceso de búsqueda.
     */
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
