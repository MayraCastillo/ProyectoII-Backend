package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Asociaciones entre hojas de vida y las empresas a las que se aplica
 * 
 * @author Ruben
 *
 */


@Entity
@Table (name = "hojas_vida_empresas")  
@IdClass(HojaVidaEmpresaId.class)
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class HojaVidaEmpresa {
	
	
	@Id
	@Column(name = "numero_documento")
	private Long numeroDocumento;
	
	@Id
	@Column(name = "nit")
	private String nitEmpresa;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "estado")
	@NotNull(message = "El estado del empleado en la empresa no puede ser Nulo") 
	private String estado;
	
	
	// RELACIONES CON OTRAS TABLAS
	
	/**
	 * Hoja de vida que una persona presenta a una empresa en especifico
	 */
	@Id
	@ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "hojas_vida.numero_documento")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private HojaVida hojaVida;
	
}
