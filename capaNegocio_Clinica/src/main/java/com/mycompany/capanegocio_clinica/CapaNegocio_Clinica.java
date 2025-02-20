/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.capanegocio_clinica;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaException;
import java.sql.Date;

/**
 *
 * @author Beto_
 */
public class CapaNegocio_Clinica {

    public static void main(String[] args) throws NegocioException, PersistenciaException {
        IConexionBD conexion = new ConexionBD();
        // Convertir el String a Date
        Date fechaNacimiento = new Date(2005 - 07 - 12);
        PacienteDAO pacienteDAO = new PacienteDAO(conexion);
//        //Metodo actualizar paciente
//        // Instancia de la clase que contiene el método 
//        

//
//        // Crear un objeto Paciente con los datos actualizados
//        Paciente pacienteActualizado = new Paciente();
//        pacienteActualizado.setIdPaciente(6);  // ID del paciente a actualizar
//        pacienteActualizado.setNombre("Juan");
//        pacienteActualizado.setApellidoPaterno("Pérez");
//        pacienteActualizado.setApellidoMaterno("Gómez");
//        pacienteActualizado.setFechaNacimiento(fechaNacimiento);
//        pacienteActualizado.setCalle("Av. Reforma");
//        pacienteActualizado.setColonia("Centro");
//        pacienteActualizado.setNumero("123");
//        pacienteActualizado.setTelefono("5551234567");
//        pacienteActualizado.setCorreo("juan.perez@example.com");
//        
//        try {
//            // Intentar actualizar el paciente en la base de datos
//            Paciente resultado = pacienteDAO.actualizarPaciente(pacienteActualizado);
//            
//            if (resultado != null) {
//                System.out.println("Paciente actualizado con éxito: " + resultado);
//            } else {
//                System.out.println("No se encontró el paciente para actualizar.");
//            }
//        } catch (PersistenciaException e) {
//            System.err.println("Error al actualizar el paciente: " + e.getMessage());
//        }
//
//        //PRUEBA AGREGAR PACIENTE
//        Paciente nuevoPaciente = new Paciente();
//        nuevoPaciente.setNombre("Ana");
//        nuevoPaciente.setApellidoPaterno("Gómez");
//        nuevoPaciente.setApellidoMaterno("Díaz");
//        nuevoPaciente.setFechaNacimiento(fechaNacimiento);
//        nuevoPaciente.setCalle("Av. Juárez");
//        nuevoPaciente.setColonia("Centro");
//        nuevoPaciente.setNumero("789");
//        nuevoPaciente.setTelefono("5551234567");
//        nuevoPaciente.setCorreo("ana.gomez@example.com");
//
//        Paciente resultado = pacienteDAO.agregarPaciente(nuevoPaciente);
//        if (resultado!=null) {
//            System.out.println("Paciente almacenado con exito");
//            
//        } else {
//            System.out.println("Algo falló no se pudo guardar el paciente");
//        }
    }
}
