/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.capapersistencia_clinica;

import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DAO.PacienteDAO;
import Entidades.Paciente;
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
        PacienteDAO pacienteBD = new PacienteDAO(conexionBD);
        
        //Pureba para agregar paciente
        try { 
            LocalDate fechaNacimiento = LocalDate.of(2005, 07, 12);
            
            // Crear el activista que vamos a guardar en la BD
            Paciente pacienteAGuardar = new Paciente("Pablo","Zamora","Gàmez",fechaNacimiento,"De la luna","Casa Blanca","2105","1928374632","pabs35@examplee.com");

            // Guardar el activista en la base de datos y el resultado lo guardamos en otro activista
            Paciente pacienteGuardado = pacienteBD.agregarPaciente(pacienteAGuardar);

            // Verificar si se guardó correctamente
            if (pacienteGuardado != null && pacienteGuardado.getIdPaciente() > 0) { // realmente con que sepamos que trae un id, puede ser tambien activistaGuardado.getIdActivista !=0
                System.out.println("Paciente guardado con éxito: " + pacienteGuardado);
            } else {
                System.out.println("No se pudo guardar el paciente.");
            }
        } catch (PersistenciaException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
