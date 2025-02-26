/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesionUsuario;

import BO.MedicoBO;
import BO.PacienteBO;
import DTO.MedicoViejoDTO;
import DTO.PacienteViejoDTO;
import configuracion.DependencyInjector;

/**
 *
 * @author Beto_
 */
public class SesionUsuario {
    private static Object usuario;
    private PacienteBO pacienteBO = DependencyInjector.crearPacienteBO();
    private MedicoBO medicoBO = DependencyInjector.crearMedicoBO();

    private SesionUsuario() {}

    public static void setUsuario(Object usuario) {
        SesionUsuario.usuario = usuario;
    }

    public static Object getUsuario() {
        return SesionUsuario.usuario;
    }

    public static void cerrarSesion() {
        SesionUsuario.usuario = null;
    }

    public static boolean esPaciente() {
        return usuario instanceof PacienteViejoDTO;
    }

    public static boolean esMedico() {
        return usuario instanceof MedicoViejoDTO;
    }

    public static PacienteViejoDTO getPaciente() {
        if (esPaciente()) {
            return (PacienteViejoDTO) usuario;
        }
        return null;
    }

    public static MedicoViejoDTO getMedico() {
        if (esMedico()) {
            return (MedicoViejoDTO) usuario;
        }
        return null;
    }
}
