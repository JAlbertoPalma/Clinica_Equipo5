/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import DTO.MedicoNuevoDTO;
import DTO.MedicoViejoDTO;
import Entidades.Consulta;
import Entidades.Medico;
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
            // Validar que el id exista
            Medico medicoBuscado = medicoDAO.obtenerMedico(idMedico);
            if (medicoBuscado == null) {
                throw new NegocioException("No hay registros de un médico con este id");
            }

            //validar que el medico este dado de alta
            if (!medicoBuscado.isEstaActivo()) {
                throw new NegocioException("Este médico ya está dado de baja");
            }

            // Intentar dar de baja al médico utilizando el DAO
            medicoDAO.darBajaMedico(idMedico);
            return true;
        } catch (PersistenciaException ex) {
            // Registrar el error y lanzar una excepción de negocio
            logger.log(Level.SEVERE, "Error al dar de baja a medico con ID: " + idMedico, ex);
            throw new NegocioException("No se pudo dar de baja el médico con ID: " + idMedico, ex);
        }
    }

    //Actualizacion de paciente
    public boolean actualizarMedico(int idMedico, MedicoNuevoDTO medicoDTO) throws NegocioException { //Funciona
        //validaciones de espacios vacíos
        if (medicoDTO.getNombre().isEmpty() || medicoDTO.getApellidoPaterno().isEmpty()
                || medicoDTO.getCedulaProfesional().isEmpty()) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }

        System.out.println("Id en la BO es: " + idMedico);
        // Mapeo del DTO a la entidad
        Medico medico = mapper.toEntity(medicoDTO);

        try {
            // Llamar a la DAO para actualizar el paciente
            medicoDAO.actualizarMedico(idMedico, medico);

        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar medico con ID: " + idMedico, ex);
            throw new NegocioException("No se pudo actualizar el medico.", ex);
        }
        return true;
    }

    public MedicoViejoDTO obtenerMedico(int idMedico) throws NegocioException {
        if (idMedico <= 0) {
            throw new NegocioException("El ID debe ser un número válido mayor que cero.");
        }
        try {
            return mapper.toViejoDTO(medicoDAO.obtenerMedico(idMedico));
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se logró obtener al medico" + ex.getMessage());
        }
    }

    public MedicoViejoDTO obtenerMedicoPorCedula(String cedula) throws NegocioException {
        try {
            return mapper.toViejoDTO(medicoDAO.obtenerMedicoPorCedula(cedula));
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se logró obtener al medico" + ex.getMessage());
        }
    }

    public List<MedicoViejoDTO> obtenerTodos() throws NegocioException {
        try {
            return mapper.toViejoDTOList(medicoDAO.obtenerMedicosActivos());
        } catch (PersistenciaException ex) {
            Logger.getLogger(MedicoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("No se lograron obtener registros" + ex.getMessage());
        }
    }

    public List<MedicoViejoDTO> obtenerMedicosActivos() throws NegocioException {
        try {
            List<Medico> MedicosEncontrados = medicoDAO.obtenerMedicosActivos();
            return mapper.toViejoDTOList(MedicosEncontrados);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener la lista de medicos activos.", e);
            throw new NegocioException("No se pudo obtener la lista de médicos activos.", e);
        }
    }

    //Consultar de historial del medico
    public List<Map<String, Object>> consultarHistorialMedico(int idMedico) throws NegocioException {//Funciona
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

    public boolean darAltaMedico(int idMedico) throws NegocioException{
        try {
            return medicoDAO.darAltaMedico(idMedico);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cambiar estado del médico.", e);
        }
    }
}
