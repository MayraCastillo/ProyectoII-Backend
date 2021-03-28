package com.example.demo.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.demo.models.ContratoPk;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contratos")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contrato implements Serializable {
	private ContratoPk contratoPk;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contrato_id")
	private Long contratoId; 
	
	@NotNull(message = "El campo tarifa arl no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arl_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TarifaArl tarifaArl;

	@Column(name = "fecha_fin_contrato")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaFinContrato;

	@Column(name = "fecha_inicio_prueba")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaIncioPrueba;

	@Column(name = "fecha_fin_prueba")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaFinPrueba;

	@Column(name = "tipo")
	@NotEmpty
	private String tipo;

	@Column(name = "estado")
	@NotEmpty
	private String estado;

	private static final long serialVersionUID = -6439404648221250056L;

}
