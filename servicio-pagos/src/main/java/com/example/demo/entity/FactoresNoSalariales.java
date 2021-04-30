package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factores_nosalariales")
public class FactoresNoSalariales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fac_nosal_id")
	private Long facNoSalId;

	@OneToOne(mappedBy = "factoresNoSalariales")
	@JsonBackReference
	private DetalleNomina detalleNomina;
	
	@Column(name = "comisiones")
	@Min(value = 0,message = "El valor del campo comisiones no puede ser negativo")
	private Double comisiones;

	@Column(name = "bonificaciones")
	@Min(value = 0, message = "El valor del campo bonificaciones no puede ser negativo")
	private Double bonificaciones;

	@Column(name = "auxilio_extra")
	@Min(value = 0, message = "El valor del campo comisiones no puede ser negativo")
	private Double auxilioExtra;

	@Column(name = "viaticos")
	@Min(value = 0, message = "El valor del campo viaticos no puede ser negativo")
	private Double viaticos;

	@Column(name = "otros")
	@Min(value = 0, message = "El valor de este campo no puede ser negativo")
	private Double otros;

}
