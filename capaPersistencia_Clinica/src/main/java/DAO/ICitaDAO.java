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
    public Cita agendarCita(Cita cita) throws PersistenciaException;
    
    public boolean cancelarCita(int idCita) throws PersistenciaException;
    
    public Cita obtenerCita(int idCita) throws PersistenciaException;
    
    public List<Cita> obtenerTodas() throws PersistenciaException;
}
