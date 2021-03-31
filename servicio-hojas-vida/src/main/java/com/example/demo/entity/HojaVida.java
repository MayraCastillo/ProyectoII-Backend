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
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.demo.DTO.DTOMunicipio;
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
	
	@Column(name = "correo")
	@NotNull(message = "El correo no puede ser nulo")
	@Email
	private String correo;
	
	@Column(name = "municipio_id")
	@NotNull(message = "El id del municipio no puede ser Nulo") 
	private Long municipioId;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "calificacion")
	@Positive(message = "La calificacion debe ser positiva") 
	private Double calificacion;
	
	@Column(name = "nit_empresa")
	@NotNull(message = "El nit de la empresa no puede ser nulo") 
	private String nitEmpresa;
	
	@Column(name = "estado_persona")
	@NotNull(message = "El estado de la persona no puede ser nulo") 
    private String estadoPersona;
	
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
	
	/**
	 * Ubicacion de residencia de la persona, municipio, departamento y pais
	 */
	@Transient
	private DTOMunicipio municipio;	
	
}
