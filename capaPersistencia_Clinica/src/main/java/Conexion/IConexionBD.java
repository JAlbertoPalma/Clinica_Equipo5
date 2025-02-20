/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Conexion;

import Exception.PersistenciaException;
import java.sql.Connection;

/**
 *
 * @author pablo
 */
public interface IConexionBD {
    public Connection crearConexion() throws PersistenciaException;
}
