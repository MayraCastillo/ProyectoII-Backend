package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Informacion basica de una hoja de vida
 * 
 * @author Ruben
 *
 */

@Entity
@Table (name = "hojas_vida")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class HojaVida {	
	
	@Id
    @Column(name = "numero_documento")
	@NotNull(message = "el numero de documento no puede ser Nulo")
	private Long numeroDocumento;
	
	@NotNull(message = "el tipo de documento no puede ser nulo")
	@Column(name = "tipo_documento")
	private String tipoDocumento;
	
	@Column(name = "nombres")
	@NotNull(message = "Los nombres no pueden ser nulos")
	private String nombres;
	
	@Column(name = "apellidos")
	@NotNull(message = "Los apellidos no pueden ser nulos")
	private String apellidos;
	
	@Column(name = "telefono")
	@NotNull(message = "El telefono no puede ser nulo")
	private String telefono;
	
	@Column(name = "ciudad")
	private String ciudad;	
	
	@Column(name = "departamento")
	private String departamento;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "calificacion")
	@Positive(message = "La calificacion debe ser positiva") 
	private Double calificacion;
	
	// RELACIONES CON OTRAS TABLAS
	
	/**
	 * Una hoja de vida contiene referencias familiares
	 */
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "numero_documento")
	@Valid
	private List<ReferenciaFamiliar> referenciasFamiliares;	
	
	/**
	 * Una hoja de vida contiene referencias personales
	 */
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "numero_documento")
	@Valid
	private List<ReferenciaPersonal> referenciasPersonales;
	
	/**
	 * En una hoja de vida se agregan las experiencias laborales que se han tenido
	 */
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "numero_documento")
	@Valid
	private List<ExperienciaLaboral> experienciasLaborales;
	
	
	/**
	 * En una hoja de vida se agregan los estudios cursados
	 */
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "numero_documento")	
	@Valid
	private List<Estudio> estudios;
	
	
	
}
