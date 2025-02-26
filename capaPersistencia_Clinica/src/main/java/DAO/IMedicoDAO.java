/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Medico;
import Exception.PersistenciaException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Beto_
 */
public interface IMedicoDAO {
    
    public void darBajaMedico(int idMedico) throws PersistenciaException;
    
    public boolean actualizarMedico(int idMedico, Medico medico) throws PersistenciaException;
    
    public Medico obtenerMedico(int idMedico) throws PersistenciaException;
    
    public Medico obtenerMedicoPorCedula(String cedula) throws PersistenciaException;
    
    public List<Medico> obtenerMedicosActivos() throws PersistenciaException;
    
    public List<Map<String, Object>> consultarHistorialConsultas(int idMedico) throws PersistenciaException;
    
    public List<Map<String, Object>> consultarAgenda(int idMedico) throws PersistenciaException;
}
