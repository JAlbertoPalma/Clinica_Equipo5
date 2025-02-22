/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import Entidades.Consulta;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class MedicoBO {

    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    private final IMedicoDAO medicoDAO;
    private final MedicoMapper mapper = new MedicoMapper(); // Usamos el mapper

    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    //Dar de baja al medico
    public boolean darBajaMedico(int idMedico) throws NegocioException {    //Funciona
        // Validar que el ID sea positivo
        if (idMedico <= 0) {
            throw new NegocioException("El ID debe ser un número válido mayor que cero.");
        }

        try {
            // Intentar dar de baja al médico utilizando el DAO
            return medicoDAO.darBajaMedico(idMedico);
        } catch (PersistenciaException ex) {
            // Registrar el error y lanzar una excepción de negocio
            logger.log(Level.SEVERE, "Error al dar de baja a medico con ID: " + idMedico, ex);
            throw new NegocioException("No se pudo dar de baja el médico con ID: " + idMedico, ex);
        }
    }

    //Consultar de historial del medico
    public List<Map<String, Object>> consultarHistorialMedico(int idMedico) throws NegocioException {
        if (idMedico <= 0) {
            throw new NegocioException("El ID del médico debe ser válido.");
        }
        try {
            return medicoDAO.consultarHistorialConsultas(idMedico);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al consultar historial médico del doctor con ID: " + idMedico, e);
            throw new NegocioException("No se pudo recuperar el historial médico.", e);
        }
    }

    //Consultar agenda del medico
    public List<Map<String, Object>> consultarAgendaMedico(int idMedico) throws NegocioException {  //Funciona
        if (idMedico <= 0) {
            throw new NegocioException("El ID del médico debe ser válido.");
        }
        try {
            return medicoDAO.consultarAgenda(idMedico);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al consultar la agenda del médico con ID: " + idMedico, e);
            throw new NegocioException("No se pudo obtener la agenda del médico.", e);
        }
    }
}
