/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "Historial.findByIdUser", query = "Select h From Historial h Where h.usuario=:usuario")

@Entity
@Table(name="historial")
@Setter
@Getter
public class Historial {
    
    @Id
    @Column( name = "id_historial")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    
    private String ciudad;
    
    @ManyToOne
    @JoinColumn( name = "usuario" )
    private Usuario usuario;
    
    @Column( name = "fecha_de_busqueda")
    private Date fechaDeBusqueda;
    
}
