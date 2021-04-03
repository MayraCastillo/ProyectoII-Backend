package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 * 
 * @author Ruben
 *
 */


@Entity
@Table (name = "referencias_personales")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ReferenciaPersonal {
	
	@Id
    @Column(name = "ref_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long referenciaId;
	
	@Column(name = "numero_documento")
	@NotNull
	private Long numeroDocumento;
	
	@Column(name = "nombres")
	@NotEmpty(message = "Los nombres de la referencia personal no pueden ser nulos")
	private String nombres;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@Column(name = "telefono")
	@NotEmpty(message = "El telefono no puede ser nulo")
	private String telefono;
	
}
