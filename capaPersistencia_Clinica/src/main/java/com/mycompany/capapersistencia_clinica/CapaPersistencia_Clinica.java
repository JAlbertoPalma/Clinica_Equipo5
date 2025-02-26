/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Beto_
 */
public class CapaPersistencia_Clinica {

    public static void main(String[] args) throws ParseException {
        // Crear la conexión a la base de datos
        IConexionBD conexionBD = new ConexionBD();
        IPacienteDAO pacienteDAO = new PacienteDAO(conexionBD);
        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        Usuario usuarioCreado = null;
        MedicoDAO medicoDAO = new MedicoDAO(conexionBD);

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
//        try { 
//            Usuario usuarioGuardar = new Usuario();
//            usuarioGuardar.setCorreo("pabs35@example.com");
//            usuarioGuardar.setContrasenia("contrasenia1");
//            usuarioGuardar.setTipo(Usuario.TipoUsuario.paciente);
//            
//            usuarioDAO.agregarUsuario(usuarioGuardar);
//            usuarioCreado = usuarioDAO.obtenerUsuarioPorCorreo("pabs35@example.com");
//            
//            LocalDate fechaNacimiento = LocalDate.of(2005, 07, 12);
//            
//            // Crear el paciente que vamos a guardar en la BD
//            Paciente pacienteAGuardar = new Paciente("Pablo" ,"Zamora" ,"Gámez" ,fechaNacimiento ,"De la luna" ,"Casa Blanca" ,
//                    "2105" ,"1928374632" , usuarioCreado.getCorreo(), usuarioCreado.getIdUsuario());
//
//            // Guardar el paciente en la base de datos y el resultado lo guardamos en otro activista
//            Paciente pacienteGuardado = pacienteDAO.agregarPaciente(pacienteAGuardar);
//
//            // Verificar si se guardó correctamente
//            if (pacienteGuardado != null && pacienteGuardado.getIdPaciente() > 0) { // realmente con que sepamos que trae un id, puede ser tambien activistaGuardado.getIdActivista !=0
//                System.out.println("Paciente guardado con éxito: " + pacienteGuardado);
//            } else {
//                System.out.println("No se pudo guardar el paciente.");
//            }
//        } catch (PersistenciaException pe) {
//            System.err.println("Error al insertar: " + pe.getMessage());
//            pe.printStackTrace();
//        }
//
//        //prueba para dar de baja medico
//        int idMedico = 3; 
//
//        try {
//            medicoDAO.darBajaMedico(idMedico);
//            System.out.println("El médico con id: " + idMedico + " ha sido dado de baja");
//        } catch (PersistenciaException e) {
//            System.err.println("Error: " + e.getMessage());
//        }

        try{
            List<Map<String, Object>> agenda = medicoDAO.consultarAgenda(1);
            
            for (Map<String, Object> map : agenda) {
                System.out.println("cita: " + map.get("id_cita") 
                                 + " nombre del paciente: " + map.get("nombre_paciente")
                                 + "fecha cita: " + map.get("horaInicio").toString());
            }
        }catch(PersistenciaException pe){
            System.out.println("Error: " + pe);
        }     
//        try{
//            List<Map<String, Object>> horarios = pacienteDAO.buscarCitasDisponibles(1, LocalDate.of(2025, 05, 11).toString());
//        
//            for (Map<String, Object> horario : horarios) {
//                System.out.println("hora inicio: " + horario.get("horaInicio")
//                                 + " hora fin: " + horario.get("horaFin"));
//            }
//        }catch(PersistenciaException pe){
//            System.out.println("Error: " + pe);
//        }

//        try{
//            System.out.println("Usuario: " + usuarioDAO.obtenerUsuarioPorCorreo("leonardo.alarcon@example.com").toString());
//            System.out.println("Paciente: " + pacienteDAO.obtenerPaciente(1).toString());
//            System.out.println("Medico: " + medicoDAO.obtenerMedico(1).toString());
//        }catch(PersistenciaException pe){
//            System.out.println("Error: " + pe);
//        }

//        try{
//            List<Medico> medicosActivos = medicoDAO.obtenerMedicosActivos();
//            List<Paciente> pacientes = pacienteDAO.obtenerPacientes();
//            List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
//            
//            System.out.println(" Usuarios");
//            for (Usuario usuario : usuarios) {
//                System.out.println(usuario.toString());
//            }
//            
//            System.out.println("\n Pacientes");
//            for (Paciente paciente : pacientes) {
//                System.out.println(paciente.toString());
//            }
//            
//            System.out.println("\n MédicosActivos");
//            for (Medico medico : medicosActivos) {
//                System.out.println(medico.toString());
//            }
//
//            
//        }catch(PersistenciaException pe){
//            
//        }
        
//        try{
//            Usuario usuarioSesion = usuarioDAO.iniciarSesionPaciente("pabs35@example.com", "contrasenia1");
//            Paciente pacienteSesion = pacienteDAO.obtenerPacientePorCorreo(usuarioSesion.getCorreo());
//            System.out.println("Bienvenido paciente: " + pacienteSesion.getNombre());
//        }catch(PersistenciaException pe){
//            System.err.println("Error: " + pe);
//        }
//        
//        try{
//            Usuario usuarioSesion = usuarioDAO.iniciarSesionMedico("8888999999", "contrasena8");
//            Medico medicoSesion = medicoDAO.obtenerMedicoPorCedula(usuarioSesion.getCedulaProfesional());
//            System.out.println("Bienvenido Medico: " + medicoSesion.getNombre());
//        }catch(PersistenciaException pe){
//            System.err.println("Error: " + pe);
//        }
//
//        
//        try {
//            // Asignar médico de urgencia
//            pacienteDAO.asignarMedicoUrgencia(1);
//        } catch (PersistenciaException ex) {
//            Logger.getLogger(CapaPersistencia_Clinica.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try{
//            usuarioDAO.encriptarContrasenias();
//        }catch(PersistenciaException pe){
//            System.out.println("Error: " + pe.getMessage());
//        }

        try{
            usuarioDAO.iniciarSesion("8888999999", "contrasena8");
            System.out.println("Bienvenido ");
        }catch(PersistenciaException pe){
            System.out.println(pe.getMessage());
        }
    }

}
