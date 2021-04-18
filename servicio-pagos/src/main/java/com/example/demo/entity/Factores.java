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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
	@NotNull(message = "El valor del campo contratoId no puede ser nulo")
	private Long contratoId;

	@Column(name = "comisiones")
	@Positive(message = "El valor del campo comisiones no puede ser negativo")
	private Double comisiones;

	@Column(name = "bonificaciones")
	@Positive(message = "El valor del campo bonificaciones no puede ser negativo")
	private Double bonificaciones;

	@Column(name = "auxilio_extra")
	@Positive(message = "El valor del campo comisiones no puede ser negativo")
	private Double auxilioExtra;

	@Column(name = "viaticos")
	@Positive(message = "El valor del campo viaticos no puede ser negativo")
	private Double viaticos;

	@Column(name = "otros")
	@Positive(message = "El valor de este campo no puede ser negativo")
	private Double otros;
	
	@Column(name = "tipo")
	@NotEmpty(message = "El valor del campo tipo no puede ser vacio")
	private String tipo;

	@Transient
	private Contrato contrato;
	private static final long serialVersionUID = 2255429766807373169L;

}
