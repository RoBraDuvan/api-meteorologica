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
import jakarta.persistence.Table;

@Entity
@Table(name="historial")
public class Historial {
    
    @Id
    @Column( name = "id_historial")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    
    private String ciudad;
    
    @ManyToOne
    @JoinColumn( name = "usuario" )
    private Usuario usuario;
    
    
}
