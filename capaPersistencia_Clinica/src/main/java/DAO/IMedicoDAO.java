/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Exception.PersistenciaException;

/**
 *
 * @author Beto_
 */
public interface IMedicoDAO {
    public void darBajaMedico(int idMedico) throws PersistenciaException;
    
    
}
