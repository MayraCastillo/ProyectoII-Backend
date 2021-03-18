package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Empresas externas, que representan los lugares 
 * en los que se ha realizado experiencias laborales
 * 
 * @author Ruben
 *
 */

@Entity
@Table (name = "empresas_externas")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class EmpresaExterna {
	
	@Id
    @Column(name = "ee_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long empresaExternaId;
	
	@Column(name = "nombre")
    @NotEmpty (message = "El nombre de la empresa no puede ser vacío")
	private String nombre;	
	
	@Column(name = "contacto")
	@NotEmpty (message = "El nombre del contacto no puede ser vacío")
	private String contacto;
	
	@Column(name = "telefono")
	@NotEmpty (message = "El telefono del contacto de la empresa externa no puede ser vacío")
	private String telefono;	
	
}
