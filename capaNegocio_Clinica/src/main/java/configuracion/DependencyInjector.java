/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import BO.CitaBO;
import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;

/**
 *
 * @author pablo
 */
public class DependencyInjector {
    public static PacienteBO crearPacienteBO(){
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);
        
        return pacienteBO;
    }
    
    public static MedicoBO crearMedicoBO(){
        IConexionBD conexion = new ConexionBD();
        MedicoBO medicoBO = new MedicoBO(conexion);
        
        return medicoBO;
    }
    
    public static UsuarioBO crearUsuarioBO(){
        IConexionBD conexion = new ConexionBD();
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        
        return usuarioBO;
    }
    
    public static CitaBO crearCitaBO(){
        IConexionBD conexion = new ConexionBD();
        CitaBO citaBO = new CitaBO(conexion);
        
        return citaBO;
    }
}
