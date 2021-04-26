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

@Entity
@Table (name = "experiencias_laborales")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ExperienciaLaboral {

	@Id
    @Column(name = "exp_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long expId;
	
	@Column(name = "numero_documento")
	//@NotNull
	private Long numeroDocumento;
	
	@Column(name = "cargo")
	@NotEmpty(message = "El cargo no puede ser nulo")
	private String cargo;	
	
	@Column(name = "tiempo")
	@NotEmpty(message = "El tiempo no puede ser nulo")
	private String tiempo;
	
	@Column(name = "calificacion")
	@NotNull
	private Double calificacion;
	
	
	
	// RELACIONES CON OTRAS TABLAS
	
	@NotNull(message = "La experiencia laboral debe estar asociada a una empresa externa")
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@Valid
    private EmpresaExterna empresaExterna;
	
	
	
}
