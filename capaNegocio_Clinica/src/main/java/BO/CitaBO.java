/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.ICitaDAO;
import DTO.CitaNuevaDTO;
import DTO.CitaViejaDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.CitaMapper;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class CitaBO {
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    private final ICitaDAO citaDAO;
    private final CitaMapper mapper = new CitaMapper(); // Usamos el mapper

    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
    }
    
    public CitaViejaDTO agendarCita(CitaNuevaDTO cita) throws NegocioException{
        try{
            return mapper.toViejoDTO(citaDAO.agendarCita(mapper.toEntity(cita)));
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    
    public boolean cancelarCita(int idCita) throws NegocioException{
        try{
            return citaDAO.cancelarCita(idCita);
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    
    public CitaViejaDTO obtenerCita(int idCita) throws NegocioException{
        try{
            return mapper.toViejoDTO(citaDAO.obtenerCita(idCita));
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    
    public List<CitaViejaDTO> obtenerTodas() throws NegocioException{
        try{
            return mapper.toViejoDTOList(citaDAO.obtenerTodas());
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
}
