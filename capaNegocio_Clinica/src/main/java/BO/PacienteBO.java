/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.PacienteMapper;
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

    public boolean agregarPaciente(PacienteNuevoDTO pacienteDTO) throws NegocioException {
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
    public Paciente actualizarActivista(int idPaciente, PacienteNuevoDTO pacienteDTO) throws NegocioException {
        
        //validaciones de espacios vacíos
        if (pacienteDTO.getNombre().isEmpty() || pacienteDTO.getApellidoPaterno().isEmpty()
         || pacienteDTO.getCalle().isEmpty() || pacienteDTO.getColonia().isEmpty()
         || pacienteDTO.getNumero().isEmpty() || pacienteDTO.getTelefono().isEmpty()
         || pacienteDTO.getCorreo().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }
        
        
        Paciente paciente = mapper.toEntity(pacienteDTO);
        paciente.setIdPaciente(idPaciente);
        try {
            // Llamar a la DAO para actualizar el paciente
            pacienteDAO.actualizarPaciente(paciente);

        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar activista con ID: " + idPaciente, ex);
            throw new NegocioException("No se pudo actualizar el activista.", ex);
        }
        return paciente;
    }
}
