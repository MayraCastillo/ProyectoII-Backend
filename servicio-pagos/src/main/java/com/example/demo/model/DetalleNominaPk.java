package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.example.demo.entity.NominaP;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleNominaPk implements Serializable
{
	@Column(name = "contrato_id")
	@NotNull(message = "El valor del campo contratoId no puede ser nulo")
	private Long contratoId;
	
	@NotNull(message = "El valor del campo pagoNomina no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nomina_id")
	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonBackReference
	private NominaP nomina;
	private static final long serialVersionUID = -5650517956133017833L;

}
