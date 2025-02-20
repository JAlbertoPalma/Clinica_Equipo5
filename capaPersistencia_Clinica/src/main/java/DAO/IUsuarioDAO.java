/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Usuario;
import java.util.List;

/**
 *
 * @author Beto_
 */
public interface IUsuarioDAO {
    public void agregarUsuario(Usuario usuario);
    
    public Usuario obtenerUsuarioPorCorreo(String correo);
    
    public List<Usuario> obtenerUsuarios();
}
