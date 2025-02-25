/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.capapersistencia_clinica;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.IUsuarioDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class Inserciones {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Crear la conexión a la base de datos
        IConexionBD conexionBD = new ConexionBD();
        IPacienteDAO pacienteDAO = new PacienteDAO(conexionBD);
        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        Usuario usuarioCreado = null;
        MedicoDAO medicoDAO = new MedicoDAO(conexionBD);
        
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
            Paciente pacienteGuardado = pacienteDAO.agregarPaciente(pacienteAGuardar);

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
        
        /**
         * Insertar 3 médicos
         */
        
        // usuario medico 1
//        try {
//            Usuario usuarioMedico1 = new Usuario();
//            usuarioMedico1.setCorreo("medico1@example.com");
//            usuarioMedico1.setContrasenia("contrasenia1");
//            usuarioMedico1.setTipo(Usuario.TipoUsuario.medico);
//            usuarioCreado = usuarioDAO.agregarUsuario(usuarioMedico1);
//            
//            Medico medico1 = new Medico("Dr. Leonardo", "Alarcon", "Jimenez", Medico.EspecialidadMedico.cardiologia, usuarioCreado.getCedulaProfesional(), true, usuarioCreado.getIdUsuario());
//            medicoDAO.
//            
//            Usuario usuarioMedico2 = new Usuario();
//            usuarioMedico1.setCorreo("medico2@example.com");
//            usuarioMedico1.setContrasenia("contrasenia2");
//            usuarioMedico1.setTipo(Usuario.TipoUsuario.medico);
//            usuarioCreado = usuarioDAO.agregarUsuario(usuarioMedico2);
//            
//            Medico medico2 = new Medico("Dr. Leonardo", "Alarcon", "Jimenez", Medico.EspecialidadMedico.cardiologia, usuarioCreado.getCedulaProfesional(), true, usuarioCreado.getIdUsuario());
//            
//            Usuario usuarioMedico3 = new Usuario();
//            usuarioMedico1.setCorreo("medico3example.com");
//            usuarioMedico1.setContrasenia("contrasenia3");
//            usuarioMedico1.setTipo(Usuario.TipoUsuario.medico);
//            usuarioCreado = usuarioDAO.agregarUsuario(usuarioMedico3);
//            
//            Medico medico3 = new Medico("Dr. Leonardo", "Alarcon", "Jimenez", Medico.EspecialidadMedico.cardiologia, usuarioCreado.getCedulaProfesional(), true, usuarioCreado.getIdUsuario());
//        } catch (PersistenciaException ex) {
//            Logger.getLogger(Inserciones.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}
