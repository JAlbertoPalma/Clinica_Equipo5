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
 * Clase de Lógica de Negocio para la gestión de citas.
 * @author Beto_
 */
public class CitaBO {
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    private final ICitaDAO citaDAO;
    private final CitaMapper mapper = new CitaMapper(); // Usamos el mapper
    
    /**
     * Constructor de CitaBO.
     * Inicializa el objeto CitaDAO con la conexión a la base de datos proporcionada.
     *
     * @param conexion Objeto IConexionBD para la conexión a la base de datos.
     */
    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
    }
    
    /**
     * Agenda una nueva cita utilizando los datos proporcionados en un DTO.
     *
     * @param cita DTO con los datos de la nueva cita.
     * @return DTO con los datos de la cita agendada.
     * @throws NegocioException Si ocurre un error durante el proceso de agendar la cita.
     */
    public CitaViejaDTO agendarCita(CitaNuevaDTO cita) throws NegocioException{
        try{
            return mapper.toViejoDTO(citaDAO.agendarCita(mapper.toEntity(cita)));
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    
    /**
     * Cancela una cita existente por su ID.
     *
     * @param idCita ID de la cita a cancelar.
     * @return true si la cancelación fue exitosa, false en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso de cancelación.
     */
    public boolean cancelarCita(int idCita) throws NegocioException{
        try{
            return citaDAO.cancelarCita(idCita);
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    /**
     * Obtiene los datos de una cita por su ID.
     *
     * @param idCita ID de la cita a obtener.
     * @return DTO con los datos de la cita.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public CitaViejaDTO obtenerCita(int idCita) throws NegocioException{
        try{
            return mapper.toViejoDTO(citaDAO.obtenerCita(idCita));
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
    
    /**
     * Obtiene una lista con todas las citas.
     *
     * @return Lista de DTOs con los datos de todas las citas.
     * @throws NegocioException Si ocurre un error durante el proceso de obtención.
     */
    public List<CitaViejaDTO> obtenerTodas() throws NegocioException{
        try{
            return mapper.toViejoDTOList(citaDAO.obtenerTodas());
        }catch(PersistenciaException pe){
            throw new NegocioException(pe.getMessage());
        }
    }
}
