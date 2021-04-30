package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registro_horas")
public class RegistroHorasP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reg_horas_id")
	private Long registroHorasId;
	
	@OneToOne(mappedBy = "registroHoras")
	@JsonBackReference
	private DetalleNomina detalleNomina;
	
	@NotNull(message = "El valor del campo horasTrabajadas no puede ser nulo")
	@Column(name = "hotas_trabajas")
	@Min(value = 0, message = "El valor del campo horasTrabajadas no puede ser negativo")
	private Integer horasTrabajadas;
	
	@NotNull(message = "El valor del campo extrasDiurnoOrdinaro no puede ser nulo")
	@Column(name = "ex_diurno_ordinario")
	@Min(value = 0, message = "El valor del campo extrasDiurnoOrdinaro no puede ser negativo")
	private Integer extrasDiurnoOrdinaro;
	
	@NotNull(message = "El valor del campo extrasNoturnoOrdinario no puede ser nulo")
	@Column(name = "ex_noturno_ordinario")
	@Min(value = 0, message = "El valor del campo extrasNoturnoOrdinario no puede ser negativo")
	private Integer extrasNoturnoOrdinario;
	
	@NotNull(message = "El valor del campo extrasDominicalFestivoDiurno no puede ser nulo")
	@Column(name = "ex_dom_fest_ordinario")
	@Min(value = 0, message = "El valor del campo extrasDominicalFestivoDiurno no puede ser negativo")
	private Integer extrasDominicalFestivoDiurno;
	
	@NotNull(message = "El valor del campo extrasDominicalFestivoNoturno no puede ser nulo")
	@Column(name = "ex_dom_fest_noturno")
	@Min(value = 0, message = "El valor del campo extrasDominicalFestivoNoturno no puede ser negativo")
	private Integer extrasDominicalFestivoNoturno;
	
	@NotNull(message = "El valor del campo recargoNoturno no puede ser nulo")
	@Column(name = "recargo_noturno")
	@Min(value = 0, message = "El valor del campo recargoNoturno no puede ser negativo")
	private Integer recargoNoturno;
	
	@NotNull(message = "El valor del campo recargoDiurnoDominicalFestivo no puede ser nulo")
	@Column(name = "rec_diurno_dom_festivo")
	@Min(value = 0, message = "El valor del campo recargoDiurnoDominicalFestivo no puede ser negativo")
	private Integer recargoDiurnoDominicalFestivo;
	
	@NotNull(message = "El valor del campo recargoNoturnoDominicalFestivo no puede ser nulo")
	@Column(name = "rec_noturno_dom_festivo")
	@Min(value = 0, message = "El valor del campo recargoNoturnoDominicalFestivo no puede ser negativo")
	private Integer recargoNoturnoDominicalFestivo;
}
