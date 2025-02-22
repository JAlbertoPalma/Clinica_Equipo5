/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
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
    
    public boolean darBajaMedico(int idMedico) throws NegocioException {    //Funciona
        if (idMedico <= 0) {
            throw new NegocioException("El ID debe ser un número válido.");
        }

        try {
            return medicoDAO.darBajaMedico(idMedico);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al eliminar medico con ID: " + idMedico, ex);
            throw new NegocioException("No se pudo eliminar el medico.", ex);
        }
    }
}
