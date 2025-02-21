/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.capanegocio_clinica;

import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import DTO.UsuarioViejoDTO;
import Entidades.Usuario;
import Exception.NegocioException;
import java.time.LocalDate;

/**
 *
 * @author Beto_
 */
public class CapaNegocio_Clinica {

    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        UsuarioViejoDTO usuario = null;
        
        UsuarioNuevoDTO usuarioGuardar = new UsuarioNuevoDTO("pabs35@example.com", "contrasenia1", Usuario.TipoUsuario.paciente);
        try{
            boolean resultadoUsuario =  usuarioBO.agregarUsuario(usuarioGuardar);
            usuario = usuarioBO.obtenerUsuarioPorCorreo("pabs35@example.com");
            
            if(resultadoUsuario){
            System.out.println("usuario almacenado con exito");

            } else{
                System.out.println("Algo falló no se pudo guardar el usuario");
            }
            
            PacienteNuevoDTO pacienteGuardar = new PacienteNuevoDTO("Pablo","Zamora","Gàmez",LocalDate.of(2005, 7, 12),
                "De la luna", "casa blanca", "233", "123456789",  usuario.getCorreo(), Integer.parseInt(usuario.getIdUsuario()));
            
            boolean resultadoPaciente =  pacienteBO.agregarPaciente(pacienteGuardar);
            if(resultadoPaciente){
                System.out.println("Paciente almacenado con exito");

            } else{
                System.out.println("Algo falló no se pudo guardar el paciente");
            }
            
        }catch(NegocioException ne){
            System.err.println("Error al insertar: " + ne.getMessage());
             ne.printStackTrace();
        }
        
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
    }
}
