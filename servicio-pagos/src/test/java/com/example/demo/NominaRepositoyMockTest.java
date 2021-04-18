package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Factores;
import com.example.demo.entity.Pago_nomina;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.RegistroHoras;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.EmpleadoNomina;
import com.example.demo.service.ServicioParametrosLegales;

@SpringBootTest
public class NominaRepositoyMockTest {
	@Autowired
	EmpresaClient empresaClient;
	
	@Autowired
	ServicioParametrosLegales parametrosLegales;
	
	public RegistroHoras crearRegistroHoras() 
	{
		RegistroHoras registroHoras = RegistroHoras.builder().contratoId(1L)
				.fechaInicio(new Date()).fechaFin(new Date()).horasTrabajadas(240)
				.extrasDiurnoOrdinaro(2).extrasNoturnoOrdinario(1).extrasDominicalFestivoDiurno(2)
				.extrasDominicalFestivoNoturno(2).recargoNoturno(2).recargoDiurnoDominicalFestivo(2)
				.recargoNoturnoDominicalFestivo(2).build();
		return registroHoras;
	}
	
	public Pago_nomina crearPagoNomina() 
	{
		Pago_nomina pagoNomina = Pago_nomina.builder().fechaInicio(new Date())
				.fechaFin(new Date()).detalle("Detalle Nomina").build();
		return pagoNomina;
	}
	
	public Factores crearFactoresSalariales() 
	{
		Factores factorSalarial = Factores.builder().contratoId(1L).comisiones(1000.0).bonificaciones(10000.0)
				.auxilioExtra(10000.0).viaticos(10000.0).otros(10000.0).tipo("SALARIAL").build();
		return factorSalarial;
	}
	
	public Factores crearFactoresNoSalariales() 
	{
		Factores factorSalarial = Factores.builder().contratoId(1L).comisiones(1000.0).bonificaciones(10000.0)
				.auxilioExtra(10000.0).viaticos(10000.0).otros(10000.0).tipo("NO SALARIAL").build();
		return factorSalarial;
	}
	
	public EmpleadoNomina crearEmpleadoNomina() {
		Contrato contrato = empresaClient.buscarContratoPorId(1L);
		EmpleadoNomina empleadoNomina = EmpleadoNomina.builder().salarioBase(contrato.getSalarioBase())
				.contratoId(contrato.getContratoId()).registroHoras(crearRegistroHoras())
				.factoresSalariales(crearFactoresSalariales()).factoresNoSalariales(crearFactoresNoSalariales())
				.pagoNomina(crearPagoNomina()).build();
		return empleadoNomina;
	}
	
	@Test
	public Double calcularBasicoDevengado() 
	{
		EmpleadoNomina empleadoNomina = crearEmpleadoNomina();
		Integer horasTrabajadas = empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = empleadoNomina.getSalarioBase();
		Double basicoDevengado = (1000000.0/240)*horasTrabajadas;
		//assertThat(basicoDevengado).isEqualTo(1000000.0);
		return basicoDevengado;
		
	}
	
	private Double RetornarSalarioMinimo() {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("SALARIO MINIMO");
		Double salarioMinimo = parametroLegal.getValor();
		return salarioMinimo;
	}
	
	public Double calcularAuxilioTransporte()
	{
		Double auxilioTransporte = 0.0;
		Integer horasTrabajadas = crearEmpleadoNomina().getRegistroHoras().getHorasTrabajadas();
		Double salarioMinimo = RetornarSalarioMinimo();
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("AUXILIO DE TRANSPORTE");
		Double valorAuxilioTransporte = parametroLegal.getValor();
		Double basicoDevengado = calcularBasicoDevengado();
		if(basicoDevengado <= 2*salarioMinimo) 
		{
			auxilioTransporte = (valorAuxilioTransporte/240)*horasTrabajadas;
		}
		return auxilioTransporte;
	}
	
	public Double calcularHorasExtras() 
	{
		EmpleadoNomina EmpleadoNomina = crearEmpleadoNomina();
		RegistroHoras registroHoras = EmpleadoNomina.getRegistroHoras();
		ParametroLegal PorcentajeExtDiurnaOrdinaria = parametrosLegales.buscarParametroPorId(5L);
		ParametroLegal PorcentajeExtNoturnaOrdinaria = parametrosLegales.buscarParametroPorId(6L);
		ParametroLegal PorcentajeExtDiurDomOfestiva = parametrosLegales.buscarParametroPorId(7L);
		ParametroLegal PorcentajeExtNoturDomOfestiva = parametrosLegales.buscarParametroPorId(8L);
		
		Double diurnaOrdinaria = (calcularBasicoDevengado()/ 240) * registroHoras.getExtrasDiurnoOrdinaro()
				* PorcentajeExtDiurnaOrdinaria.getValor();
		Double noturnaOrdinaria = (calcularBasicoDevengado() / 240) * registroHoras.getExtrasNoturnoOrdinario()
				* PorcentajeExtNoturnaOrdinaria.getValor();
		Double diurnaDomFestiva = (calcularBasicoDevengado() / 240)* registroHoras.getExtrasDominicalFestivoDiurno()
				* PorcentajeExtDiurDomOfestiva.getValor();
		Double noturnoDomFestiva = (calcularBasicoDevengado()/ 240)* registroHoras.getExtrasDominicalFestivoNoturno()
				* PorcentajeExtNoturDomOfestiva.getValor();
		Double horasExtras = diurnaOrdinaria + noturnaOrdinaria + diurnaDomFestiva + noturnoDomFestiva;
		return horasExtras;
	}

}
