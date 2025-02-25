/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.capanegocio_clinica;

import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DTO.UsuarioViejoDTO;
import Entidades.ConsultaUrgencia;
import Exception.NegocioException;
import Exception.PersistenciaException;
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
public class CapaNegocio_Clinica {

    public static void main(String[] args) throws PersistenciaException {
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        MedicoBO medicoBO = new MedicoBO(conexion);
        UsuarioViejoDTO usuario = null;

//        UsuarioNuevoDTO usuarioGuardar = new UsuarioNuevoDTO("pabs35@example.com", "contrasenia1", Usuario.TipoUsuario.paciente);
//        try{
//            boolean resultadoUsuario =  usuarioBO.agregarUsuario(usuarioGuardar);
//            usuario = usuarioBO.obtenerUsuarioPorCorreo("pabs35@example.com");
//            
//            if(resultadoUsuario){
//            System.out.println("usuario almacenado con exito");
//
//            } else{
//                System.out.println("Algo falló no se pudo guardar el usuario");
//            }
//            
//            PacienteNuevoDTO pacienteGuardar = new PacienteNuevoDTO("Pablo","Zamora","Gàmez",LocalDate.of(2005, 7, 12),
//                "De la luna", "casa blanca", "233", "123456789",  usuario.getCorreo(), Integer.parseInt(usuario.getIdUsuario()));
//            
//            boolean resultadoPaciente =  pacienteBO.agregarPaciente(pacienteGuardar);
//            if(resultadoPaciente){
//                System.out.println("Paciente almacenado con exito");
//
//            } else{
//                System.out.println("Algo falló no se pudo guardar el paciente");
//            }
//            
//        }catch(NegocioException ne){
//            System.err.println("Error al insertar: " + ne.getMessage());
//             ne.printStackTrace();
//        }
//        PacienteNuevoDTO pacienteGuardar = new PacienteNuevoDTO("Pablo","Zamora","Gàmez",LocalDate.of(2005, 7, 12),
//                "De la luna", "casa blanca", "233", "123456789",  usuario.getCorreo(), Integer.parseInt(usuario.getIdUsuario()));
//        try{
//            boolean resultado =  pacienteBO.agregarPaciente(pacienteGuardar);
//            if(resultado){
//            System.out.println("Activista almacenado con exito");
//
//            } else{
//                System.out.println("Algo falló no se pudo guardar el activista");
//            }
//        }catch(NegocioException ne){
//            System.err.println("Error al insertar: " + ne.getMessage());
//             ne.printStackTrace();
//        }
//        
//        PacienteNuevoDTO pacienteGuardar2 = new PacienteNuevoDTO("", "Zamora","Gàmez",LocalDate.of(2005, 7, 12),"De la luna","Casa Blanca","2105","1928374632","pabs35@exampleee.com");
//        try{
//            boolean resultado2 =  pacienteBO.agregarPaciente(pacienteGuardar2);
//            if(resultado2){
//            System.out.println("Activista almacenado con exito");
//
//            } else{
//                System.out.println("Algo falló no se pudo guardar el activista");
//            }
//        }catch(NegocioException ne){
//            System.err.println("Error al insertar: " + ne.getMessage());
//             ne.printStackTrace();
//        } 
//        //VISTAS
//        //----------------------------------------MEDICO-------------------------------------------------
//        //Prueba eliminar Medico
//        try {
//            boolean resultado = medicoBO.darBajaMedico(3);
//            if (resultado) {
//                System.out.println("Medico eliminado");
//            } else {
//                System.out.println("Algo fallo al dar de baja al medico");
//            }
//        } catch (NegocioException ne) {
//            System.err.println("Error al insertar: " + ne.getMessage());
//            ne.printStackTrace();
//        }
//
//        //Prueba consultar agenda de medico
//        try {
//            int idMedico = 1;
//            List<Map<String, Object>> agenda = medicoBO.consultarAgendaMedico(idMedico);
//            
//            System.out.println("Agenda del Médico (ID: " + idMedico + ")");
//            for (Map<String, Object> cita : agenda) {
//                System.out.println("Cita ID: " + cita.get("id_cita"));
//                System.out.println("Paciente: " + cita.get("nombre_paciente") + " " + cita.get("apellido_paciente"));
//                System.out.println("Hora Inicio: " + cita.get("horaInicio"));
//                System.out.println("Hora Fin: " + cita.get("horaFin"));
//                System.out.println("Estado: " + cita.get("estado"));
//                System.out.println("---------------------------------");
//            }
//        } catch (NegocioException e) {
//            System.err.println("Error: " + e.getMessage());
//        }
////
////        //Consulta historial de consultas del medico
//        try {
//            int idMedico = 1;
//            
//            List<Map<String, Object>> historial = medicoBO.consultarHistorialMedico(idMedico);
//            
//            System.out.println("Historial Médico del Doctor con ID: " + idMedico);
//            for (Map<String, Object> consulta : historial) {
//                System.out.println("ID Cita: " + consulta.get("id_cita"));
//                System.out.println("Paciente: " + consulta.get("nombre_paciente") + " " + consulta.get("apellido_paciente"));
//                System.out.println("Hora Inicio: " + consulta.get("horaInicio"));
//                System.out.println("Hora Fin: " + consulta.get("horaFin"));
//                System.out.println("Estado: " + consulta.get("estado"));
//                System.out.println("---------------------------------");
//            }
//        } catch (NegocioException e) {
//            System.err.println("Error: " + e.getMessage());
//        }
//
//        //---------------------------------PACIENTE------------------------------------------------------
//        //Consulta historial de consultas del paciente
//        try {
//            int idPaciente = 1; // ID del paciente
//            String tipoConsulta = "Cardiología";
//            LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
//            LocalDate fechaFin = LocalDate.of(2024, 12, 31);
//            
//            List<Map<String, Object>> historial = pacienteBO.consultarHistorialConsultasPaciente(idPaciente, tipoConsulta, fechaInicio, fechaFin);
//            
//            System.out.println("Historial de Consultas del Paciente con ID: " + idPaciente);
//            for (Map<String, Object> consulta : historial) {
//                System.out.println("Consulta ID: " + consulta.get("id_consulta"));
//                System.out.println("Médico: " + consulta.get("nombre_medico") + " " + consulta.get("apellidoPat_medico") + " " + consulta.get("apellidoMat_medico"));
//                System.out.println("Tipo de consulta: " + consulta.get("tipo"));
//                System.out.println("Fecha y Hora: " + consulta.get("fechaHora"));
//                System.out.println("Estado: " + consulta.get("estado"));
//                System.out.println("Diagnóstico: " + consulta.get("diagnostico"));
//                System.out.println("Tratamiento: " + consulta.get("tratamiento"));
//                System.out.println("---------------------------------");
//            }
//        } catch (NegocioException e) {
//            System.err.println("Error: " + e.getMessage());
//        }
        //Consultar citas disponibles
//        try {
//            int idMedico = 1;
//            String fechaCita = "2025-02-24";
//            List<Map<String, Object>> horarios = pacienteBO.consultarCitasDisponibles(idMedico, fechaCita);
//            System.out.println("Horarios disponibles para el medico con id " + idMedico);
//            for (Map<String, Object> horario : horarios) {
//                System.out.println("Inicio: " + horario.get("horaInicio") + " - Fin: " + horario.get("horaFin"));
//            }
//        }catch(NegocioException e){
//            e.printStackTrace();
//        }
//        
//        try {
//            System.out.println("Usuario: " + usuarioBO.obtenerUsuarioPorCorreo("2"));
//            System.out.println("Paciente: " + pacienteBO.obtenerPaciente(1));
//            System.out.println("Medico: " + medicoBO.obtenerMedico(1));
//        } catch (NegocioException ex) {
//            Logger.getLogger(CapaNegocio_Clinica.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //Prueba asignar medico Urgencia
        int idPaciente = 1;
        String nombreMedico = "Dr Leonardo";
        LocalTime HoraInicio = LocalTime.of(10, 00);
        LocalTime HoraFin = LocalTime.of(14, 00);
        ConsultaUrgencia consultaU = pacienteBO.asignarMedicoUrgencia(idPaciente, nombreMedico, HoraInicio, HoraFin);
        System.out.println(consultaU);
    }
}
