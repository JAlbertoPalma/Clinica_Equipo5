/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.IConexionBD;
import Entidades.ConsultaUrgencia;
import Entidades.Medico;
import Entidades.Paciente;
import Exception.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion;
    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    //agregar Paciente
    @Override
    public Paciente agregarPaciente(Paciente paciente) throws PersistenciaException {//Funciona
        // consulta SQL que vamos a ejecutar en mysql
        String sentenciaSQL = "INSERT INTO pacientes (nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo, id_usuario)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros de la consulta
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setObject(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getCalle());
            ps.setString(6, paciente.getColonia());
            ps.setString(7, paciente.getNumero());
            ps.setString(8, paciente.getTelefono());
            ps.setString(9, paciente.getCorreo());
            ps.setInt(10, paciente.getIdUsuario());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                logger.severe("La creación del paciente falló");
                throw new PersistenciaException("La creación del paciente falló");
            }
            // Se obtiene el ID generado por la base de datos 
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paciente.setIdPaciente(generatedKeys.getInt(1));
                    logger.info("Paciente creado exitosamente con ID: " + paciente.getIdPaciente());
                } else {
                    logger.severe("La creación del paciente falló, no se obtuvo ID.");
                    throw new PersistenciaException("La creación del paciente falló, no se obtuvo ID.");
                }
            }
            return paciente;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear paciente", e);
            throw new PersistenciaException("Error al crear al paciente", e);
        }
    }

    //ACTUALIZAR PACIENTE
    @Override
    public boolean actualizarPaciente(int idPaciente, Paciente paciente) throws PersistenciaException {//Funciona
        String consultaSQL = "UPDATE pacientes SET nombre = ?, apellidoPat=?, apellidoMat=?, fechaNacimiento=?, calle=?, colonia=?, numero=?, telefono=?, correo=? WHERE id = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {
            // Asignamos los parámetros correctamente
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setObject(4, paciente.getFechaNacimiento());
            ps.setString(5, paciente.getCalle());
            ps.setString(6, paciente.getColonia());
            ps.setString(7, paciente.getNumero());
            ps.setString(8, paciente.getTelefono());
            ps.setString(9, paciente.getCorreo());
            ps.setInt(10, idPaciente); // WHERE id = ?
            // Ejecutamos la actualización
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar paciente con ID: " + paciente.getIdPaciente(), e);
            throw new PersistenciaException("Error al actualizar paciente con ID " + paciente.getIdPaciente(), e);
        }
    }

    @Override
    public Paciente obtenerPaciente(int idPaciente) throws PersistenciaException {
        // auxiliar de usuario
        Paciente paciente = null;
        String tipo;

        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo, id_usuario FROM pacientes WHERE id = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setInt(1, idPaciente);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto paciente y se asignan sus propiedades
                    paciente = new Paciente();

                    paciente.setIdPaciente(rs.getInt("id"));
                    paciente.setNombre(rs.getString("nombre"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPat"));
                    paciente.setApellidoMaterno(rs.getString("apellidoMat"));
                    paciente.setFechaNacimiento(rs.getObject("fechaNacimiento", LocalDate.class));
                    paciente.setCalle(rs.getString("calle"));
                    paciente.setColonia(rs.getString("colonia"));
                    paciente.setNumero(rs.getString("numero"));
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setCorreo(rs.getString("correo"));
                    paciente.setIdUsuario(rs.getInt("id_usuario"));

                    logger.info("Paciente encontrado: " + paciente);
                } else {
                    logger.warning("No se encontró el paciente con id: " + idPaciente); // no es error, solo advertencia
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar paciente con id: " + idPaciente, e);
            throw new PersistenciaException("Error al consultar paciente por id " + idPaciente, e);

        }
        return paciente;
    }

    @Override
    public Paciente obtenerPacientePorCorreo(String correo) throws PersistenciaException {
        // auxiliar de usuario
        Paciente paciente = null;
        String tipo;

        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo, id_usuario FROM pacientes WHERE correo = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL)) {

            // Asignamos el parámetro ID de la consulta 
            ps.setString(1, correo);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //verificamos que se haya obtenido algo
                    // Se crea el objeto paciente y se asignan sus propiedades
                    paciente = new Paciente();

                    paciente.setIdPaciente(rs.getInt("id"));
                    paciente.setNombre(rs.getString("nombre"));
                    paciente.setApellidoPaterno(rs.getString("apellidoPat"));
                    paciente.setApellidoMaterno(rs.getString("apellidoMat"));
                    paciente.setFechaNacimiento(rs.getObject("fechaNacimiento", LocalDate.class));
                    paciente.setCalle(rs.getString("calle"));
                    paciente.setColonia(rs.getString("colonia"));
                    paciente.setNumero(rs.getString("numero"));
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setCorreo(rs.getString("correo"));
                    paciente.setIdUsuario(rs.getInt("id_usuario"));

                    logger.info("Paciente encontrado: " + paciente);
                } else {
                    logger.warning("No se encontró el paciente con correo: " + correo); // no es error, solo advertencia
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al consultar paciente con correo: " + correo, e);
            throw new PersistenciaException("Error al consultar paciente por correo " + correo, e);

        }
        return paciente;
    }

    @Override
    public List<Paciente> obtenerPacientes() throws PersistenciaException {
        String consultaSQL = "SELECT id, nombre, apellidoPat, apellidoMat, fechaNacimiento, calle, colonia, numero, telefono, correo, id_usuario FROM pacientes";

        // Lista donde se almacenarán los usuarios recuperados
        List<Paciente> pacientes = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(consultaSQL); ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
                ) {
            // Se recorre el ResultSet mientras haya filas disponibles con el next()
            while (rs.next()) {

                // Se crea el objeto medico y se asignan sus propiedades
                Paciente paciente = new Paciente();

                paciente.setIdPaciente(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellidoPaterno(rs.getString("apellidoPat"));
                paciente.setApellidoMaterno(rs.getString("apellidoMat"));
                paciente.setFechaNacimiento(rs.getObject("fechaNacimiento", LocalDate.class));
                paciente.setCalle(rs.getString("calle"));
                paciente.setColonia(rs.getString("colonia"));
                paciente.setNumero(rs.getString("numero"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setCorreo(rs.getString("correo"));
                paciente.setIdUsuario(rs.getInt("id_usuario"));

                // Se agrega el usuario a la lista
                pacientes.add(paciente);
            }

            // Se retorna la lista con todos los usuarios obtenidos
            return pacientes;

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error al obtener la lista de pacientes.", ex);
        }
    }

    //Consulta historial de consultas del paciente
    @Override
    public List<Map<String, Object>> consultarHistorialConsultas(int idPaciente, String tipoConsulta, LocalDate fechaInicio, LocalDate fechaFin) throws PersistenciaException {//Funciona
        List<Map<String, Object>> historial = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id_consulta, nombre_medico, apellidoPat_medico, apellidoMat_medico, fechaHora, tipo, estado, tratamiento, diagnostico FROM vista_historial_consultas_paciente WHERE id_paciente = ?");
        if (tipoConsulta != null && !tipoConsulta.isEmpty()) {
            sql.append(" AND tipo = ?");
        }
        if (fechaInicio != null && fechaFin != null) {
            sql.append(" AND DATE(fechaHora) BETWEEN ? AND ?");
        }
        try (Connection con = this.conexion.crearConexion(); PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            int index = 1;
            pstmt.setInt(index++, idPaciente);
            if (tipoConsulta != null && !tipoConsulta.isEmpty()) {
                pstmt.setString(index++, tipoConsulta);
            }
            if (fechaInicio != null && fechaFin != null) {
                pstmt.setDate(index++, Date.valueOf(fechaInicio));
                pstmt.setDate(index++, Date.valueOf(fechaFin));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> consulta = new HashMap<>();
                consulta.put("id_consulta", rs.getInt("id_consulta"));
                consulta.put("nombre_medico", rs.getString("nombre_medico"));
                consulta.put("apellidoPat_medico", rs.getString("apellidoPat_medico"));
                consulta.put("apellidoMat_medico", rs.getString("apellidoMat_medico"));
                consulta.put("fechaHora", rs.getTimestamp("fechaHora").toLocalDateTime());
                consulta.put("tipo", rs.getString("tipo"));
                consulta.put("estado", rs.getString("estado"));
                consulta.put("tratamiento", rs.getString("tratamiento"));
                consulta.put("diagnostico", rs.getString("diagnostico"));
                historial.add(consulta);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el historial de consultas del paciente.", e);
        }
        return historial;
    }

    //Buscar Citas disponibles
    @Override
    public List<Map<String, Object>> buscarCitasDisponibles(int idMedico, String fechaCita) throws PersistenciaException {//Funciona
        List<Map<String, Object>> horariosDisponibles = new ArrayList<>();
        try (Connection con = this.conexion.crearConexion();) {
            CallableStatement pstmt = con.prepareCall("CALL buscar_citas_disponibles(?, ?)");
            pstmt.setInt(1, idMedico);
            pstmt.setString(2, fechaCita);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> horario = new HashMap<>();
                    horario.put("horaInicio", rs.getTime("horaInicio"));
                    horario.put("horaFin", rs.getTime("horaFin"));
                    horariosDisponibles.add(horario);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horariosDisponibles;
    }

    //Asignar medico a urgencias
    @Override
    public ConsultaUrgencia asignarMedicoUrgencia(int idPaciente) throws PersistenciaException {
        try (Connection con = this.conexion.crearConexion();) {
            CallableStatement pstmt = con.prepareCall("CALL asignar_medico_urgencia(?,?,?,?,?)");
            pstmt.setInt(1, idPaciente);
            pstmt.registerOutParameter(2, Types.VARCHAR);
            pstmt.registerOutParameter(3, Types.TIME);
            pstmt.registerOutParameter(4, Types.TIME);
            pstmt.registerOutParameter(5, Types.VARCHAR);
            pstmt.execute();

            String nombreMedico = pstmt.getString(2);
            LocalTime horaInicio = pstmt.getTime(3).toLocalTime();
            LocalTime horaFin = pstmt.getTime(4).toLocalTime();
            String folio =pstmt.getString(5);

            return new ConsultaUrgencia(nombreMedico, horaInicio, horaFin, folio);
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new PersistenciaException("Error al asignar médico de urgencia: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int EncontraridPaciente(String correo) throws PersistenciaException {
        int idPaciente = 0;
        String sql = "SELECT id FROM pacientes WHERE correo = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idPaciente = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar el ID del paciente", e);
        }
        return idPaciente;
    }
}
