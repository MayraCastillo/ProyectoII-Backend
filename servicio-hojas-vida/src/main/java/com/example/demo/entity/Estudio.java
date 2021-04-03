package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un estudio que un aspirante ha cursado y anexa a su hoja de vida
 * 
 * @author Ruben
 *
 */


@Entity
@Table (name = "estudios")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Estudio {
	
	@Id
    @Column(name = "est_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long estudioId;
	
	@Column(name = "numero_documento")
	@NotNull
	private Long numeroDocumento;
	
	@Column(name = "nombre_titulo")
	@NotEmpty(message = "El nombre del titulo no puede ser nulo")
	private String nombreTitulo;
	
	@Column(name = "calificacion")
	@NotNull
	private Double calificacion;
	
	@Column(name = "tipo")
	@NotEmpty(message = "El tipo no puede ser nulo")
	private String tipo;
	
	@Column(name = "tiempo")
	@NotEmpty(message = "El tiempo de estudio no puede ser nulo")
	private String tiempo;
	
	
	// RELACIONES CON OTRAS TABLAS
	
	@NotNull(message = "Los estudios deben estar asociados a una institucion educativa")
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "inst_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@Valid
    private InstitucionEducativa institucionEducativa;
	
}
