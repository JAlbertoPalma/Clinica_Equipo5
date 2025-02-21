/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.capapersistencia_clinica;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.IUsuarioDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author Beto_
 */
public class CapaPersistencia_Clinica {

    public static void main(String[] args) throws ParseException {
        // Crear la conexión a la base de datos
        IConexionBD conexionBD = new ConexionBD();
        IPacienteDAO pacienteBD = new PacienteDAO(conexionBD);
        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        Usuario usuarioCreado = null;
        
//        //Prueba para agregar al usuario
//        try{
//            Usuario usuarioGuardar = new Usuario();
//            usuarioGuardar.setCorreo("pabs35@example.com");
//            usuarioGuardar.setContrasenia("contrasenia1");
//            usuarioGuardar.setTipo(Usuario.TipoUsuario.paciente);
//            
//            usuarioDAO.agregarUsuario(usuarioGuardar);
//            usuarioCreado = usuarioDAO.obtenerUsuarioPorCorreo("pabs35@example.com");
//            
//        } catch(PersistenciaException pe){
//            System.err.println("Error al insertar: " + pe.getMessage());
//            pe.printStackTrace();
//        }
        
        //Pureba para agregar paciente
        try { 
            Usuario usuarioGuardar = new Usuario();
            usuarioGuardar.setCorreo("pabs35@example.com");
            usuarioGuardar.setContrasenia("contrasenia1");
            usuarioGuardar.setTipo(Usuario.TipoUsuario.paciente);
            
            usuarioDAO.agregarUsuario(usuarioGuardar);
            usuarioCreado = usuarioDAO.obtenerUsuarioPorCorreo("pabs35@example.com");
            
            LocalDate fechaNacimiento = LocalDate.of(2005, 07, 12);
            
            // Crear el paciente que vamos a guardar en la BD
            Paciente pacienteAGuardar = new Paciente("Pablo" ,"Zamora" ,"Gámez" ,fechaNacimiento ,"De la luna" ,"Casa Blanca" ,
                    "2105" ,"1928374632" , usuarioCreado.getCorreo(), usuarioCreado.getIdUsuario());

            // Guardar el paciente en la base de datos y el resultado lo guardamos en otro activista
            Paciente pacienteGuardado = pacienteBD.agregarPaciente(pacienteAGuardar);

            // Verificar si se guardó correctamente
            if (pacienteGuardado != null && pacienteGuardado.getIdPaciente() > 0) { // realmente con que sepamos que trae un id, puede ser tambien activistaGuardado.getIdActivista !=0
                System.out.println("Paciente guardado con éxito: " + pacienteGuardado);
            } else {
                System.out.println("No se pudo guardar el paciente.");
            }
        } catch (PersistenciaException pe) {
            System.err.println("Error al insertar: " + pe.getMessage());
            pe.printStackTrace();
        }
    }

}
