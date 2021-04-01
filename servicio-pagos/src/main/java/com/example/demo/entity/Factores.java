package com.example.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.example.demo.model.Contrato;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factores")
public class Factores implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "factor_id")
	private Long factorId;

	@Column(name = "contrato_id")
	private Long contratoId;

	@Column(name = "comisiones")
	private Double comisiones;

	@Column(name = "bonificaciones")
	private Double bonificaciones;

	@Column(name = "auxilio_extra")
	private Double auxilioExtra;

	@Column(name = "viaticos")
	private Double viaticos;

	@Column(name = "otros")
	private Double otros;
	
	@Column(name = "tipo")
	@NotEmpty
	private String tipo;

	@Transient
	private Contrato contrato;
	private static final long serialVersionUID = 2255429766807373169L;

}
