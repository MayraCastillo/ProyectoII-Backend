package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.example.demo.model.Contrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registros_horas")
public class RegistroHoras implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reg_horas_id")
	private Long registroHorasId;
	
	@Column(name = "contrato_id")
	private Long contratoId;
	
	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaFin;
	
	@Column(name = "hotas_trabajas")
	private Integer horasTrabajadas;
	
	@Column(name = "ex_diurno_ordinario")
	private Integer extrasDiurnoOrdinaro;
	
	@Column(name = "ex_noturno_ordinario")
	private Integer extrasNoturnoOrdinario;
	
	@Column(name = "ex_dom_fest_ordinario")
	private Integer extrasDominicalFestivoDiurno;
	
	@Column(name = "ex_dom_fest_noturno")
	private Integer extrasDominicalFestivoNoturno;
	
	@Column(name = "recargo_noturno")
	private Integer recargoNoturno;
	
	@Column(name = "rec_diurno_dom_festivo")
	private Integer recargoDiurnoDominicalFestivo;
	
	@Column(name = "rec_noturno_dom_festivo")
	private Integer recargoNoturnoDominicalFestivo;

	@Transient
	private Contrato contrato;
	
	private static final long serialVersionUID = 7233669275440910113L;

}
