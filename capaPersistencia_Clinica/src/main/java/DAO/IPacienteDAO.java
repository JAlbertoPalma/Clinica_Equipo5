/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Paciente;
import Exception.PersistenciaException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pablo
 */
public interface IPacienteDAO {
     
    /**
     *
     * @param paciente
     * @return
     * @throws PersistenciaException
     */
    public Paciente agregarPaciente(Paciente paciente)throws PersistenciaException;

    public boolean actualizarPaciente(Paciente paciente)throws PersistenciaException;
    
    public Paciente obtenerPaciente(int idPaciente) throws PersistenciaException;
    
    public List<Map<String, Object>> consultarHistorialConsultas(int idPaciente, String tipoConsulta, LocalDate fechaInicio, LocalDate fechaFin) throws PersistenciaException;
    
    public List<Map<String, Object>> buscarCitasDisponibles(int idMedico, String fechaCita)throws PersistenciaException;
}
