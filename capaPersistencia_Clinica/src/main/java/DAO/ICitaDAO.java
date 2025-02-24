/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Exception.PersistenciaException;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface ICitaDAO {
    
    public boolean agendarCita(Cita cita) throws PersistenciaException;
    
    public List<Cita> obtenerHistorialCitasMedico(int idPaciente) throws PersistenciaException;
    
    
}
