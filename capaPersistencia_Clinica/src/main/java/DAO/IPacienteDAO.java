/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Paciente;
import Exception.PersistenciaException;

/**
 *
 * @author pablo
 */
public interface IPacienteDAO {
     
    public Paciente agregarPaciente(Paciente paciente)throws PersistenciaException;
        
    public boolean actualizarPaciente(Paciente paciente)throws PersistenciaException;
}
