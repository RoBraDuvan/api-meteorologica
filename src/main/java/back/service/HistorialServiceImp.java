/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.service;

import back.entity.Historial;
import back.entity.Usuario;
import back.repository.HistorialRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistorialServiceImp implements HistorialService {
    
    @Autowired
    HistorialRepository historialRepository;

    @Override
    public void saveHistorial(String ciudad, Usuario usuario) {
        
        Historial historial = new Historial();

        historial.setCiudad(ciudad);
        historial.setUsuario(usuario);
        historial.setFechaDeBusqueda(new Date());
        
        this.historialRepository.save(historial);
    }
    
}
