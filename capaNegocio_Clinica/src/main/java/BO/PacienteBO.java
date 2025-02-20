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
    
        public boolean agregarPaciente(PacienteNuevoDTO pacienteNuevo) throws NegocioException {
        if (pacienteNuevo == null) {
            throw new NegocioException("El paciente no puede ser nulo.");
        }

        // Validaciones 

        // Convertimos el DTO a la entidad
        Paciente paciente = mapper.toEntity(pacienteNuevo);

        try {
            Paciente pacienteGuardado = pacienteDAO.agregarPaciente(paciente);
            return pacienteGuardado != null;
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al guardar paciente en la Base de Datos", ex);
            throw new NegocioException("Hubo un error al guardar el paciente.", ex);
        }
    }
}
