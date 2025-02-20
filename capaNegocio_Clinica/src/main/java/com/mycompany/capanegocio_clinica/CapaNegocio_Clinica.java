/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.capanegocio_clinica;

import BO.PacienteBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DTO.PacienteNuevoDTO;
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
        
        PacienteNuevoDTO pacienteGuardar = new PacienteNuevoDTO("Pablo","Zamora","Gàmez",LocalDate.of(2005, 7, 12),"De la luna","Casa Blanca","2105","1928374632","pabs35@exampleee.com");
        try{
            boolean resultado =  pacienteBO.agregarPaciente(pacienteGuardar);
            if(resultado){
            System.out.println("Activista almacenado con exito");

            } else{
                System.out.println("Algo falló no se pudo guardar el activista");
            }
        }catch(NegocioException ne){
            System.err.println("Error al insertar: " + ne.getMessage());
             ne.printStackTrace();
        }
        
        PacienteNuevoDTO pacienteGuardar2 = new PacienteNuevoDTO("", "Zamora","Gàmez",LocalDate.of(2005, 7, 12),"De la luna","Casa Blanca","2105","1928374632","pabs35@exampleee.com");
        try{
            boolean resultado2 =  pacienteBO.agregarPaciente(pacienteGuardar2);
            if(resultado2){
            System.out.println("Activista almacenado con exito");

            } else{
                System.out.println("Algo falló no se pudo guardar el activista");
            }
        }catch(NegocioException ne){
            System.err.println("Error al insertar: " + ne.getMessage());
             ne.printStackTrace();
        } 
    }
}
