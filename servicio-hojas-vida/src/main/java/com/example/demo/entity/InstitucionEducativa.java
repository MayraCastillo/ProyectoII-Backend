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
 * Instituciones educativas
 * 
 * @author Ruben
 *
 */


@Entity
@Table (name = "instituciones_educativas")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class InstitucionEducativa {

	@Id
    @Column(name = "inst_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long instId;
	
	@Column(name = "nombre")
    @NotEmpty (message = "El nombre de la institución no puede ser vacío")
	private String nombre;	
	
}
