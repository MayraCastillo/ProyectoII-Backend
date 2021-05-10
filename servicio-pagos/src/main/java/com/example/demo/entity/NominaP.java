package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "nominasP")
public class NominaP implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nomina_id")
	private Long nominaId;

	@OneToMany(mappedBy = "detalleNominaPk.nomina")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<DetalleNomina> listaDetalleNomina;

	@NotNull(message = "El valor del campo fechaInicio no puede ser nulo")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@NotNull(message = "El valor del campo fechaFin no puede ser nulo")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "fecha_fin")
	private Date fechaFin;

	@NotEmpty(message = "El valor del campo detalle no puede ser vacio")
	@Column(name = "detalle")
	private String detalle;

	@NotEmpty(message = "El valor de campo estado no puede se vacio")
	@Column(name = "estado")
	private String estado;

	private static final long serialVersionUID = 5029340493016367266L;
}
