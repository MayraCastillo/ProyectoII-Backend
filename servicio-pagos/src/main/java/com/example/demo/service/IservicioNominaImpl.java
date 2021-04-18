package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.FactoresDAO;
import com.example.demo.dao.NominaDAO;
import com.example.demo.dao.Pago_nominaDAO;
import com.example.demo.dao.ParametrosLegalesDAO;
import com.example.demo.dao.RegistroHorasDAO;
import com.example.demo.entity.Factores;
import com.example.demo.entity.Nomina;
import com.example.demo.entity.Pago_nomina;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.RegistroHoras;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.EmpleadoNomina;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IservicioNominaImpl implements IservicioNomina {

	@Autowired
	RegistroHorasDAO registroHorasDAO;

	@Autowired
	FactoresDAO factoresDAO;

	@Autowired
	Pago_nominaDAO pagoNominaDAO;

	@Autowired
	NominaDAO nominaDAO;

	@Autowired
	ParametrosLegalesDAO parametrosLegalesDAO;

	@Autowired
	ServicioParametrosLegales parametrosLegales;

	@Autowired
	EmpresaClient empresaClient;

	@Override
	public RegistroHoras crearRegistroHoras(RegistroHoras pRegistroHoras) {
		return registroHorasDAO.save(pRegistroHoras);
	}

	@Override
	public Factores crearFactores(Factores pFactores) {
		return factoresDAO.save(pFactores);
	}

	@Override
	public Pago_nomina crearPagoNomina(Pago_nomina pPagoNomina) {

		return pagoNominaDAO.save(pPagoNomina);
	}

	@Override
	public Nomina crearNomina(Nomina pNomina) {
		return nominaDAO.save(pNomina);
	}

	@Override
	public Nomina CalcularNomina(EmpleadoNomina pEmpleadoNomina) {

		Contrato contrato = empresaClient.buscarContratoPorId(pEmpleadoNomina.getContratoId());
		Double basicoDevengado = redondeo(basicoDevengado(pEmpleadoNomina));
		Integer horasTrabajadas = pEmpleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double horasExtras = redondeo(horasExtras(pEmpleadoNomina));
		Double auxilioTransporte = redondeo(auxilioTransporte(horasTrabajadas, basicoDevengado));
		Double recargos = redondeo(recargos(pEmpleadoNomina));

		Double comisiones = redondeo(pEmpleadoNomina.getFactoresSalariales().getComisiones());
		Double ingresoSalarial = redondeo(otrosIngresoSalarial(pEmpleadoNomina));
		Double ingresoNoSalarial = redondeo(otrosIngresoNoSalarial(pEmpleadoNomina));
		Double totalDevengado = redondeo(basicoDevengado + auxilioTransporte + horasExtras + recargos + comisiones
				+ ingresoSalarial + ingresoNoSalarial);
		Double devengadoMenosNoSalarial = redondeo(totalDevengado - ingresoNoSalarial);
		Double devMenosNosalMenosAuxTrans = redondeo(devengadoMenosNoSalarial - auxilioTransporte);
		Double saludEmpleado = redondeo(saludEmpleado(devMenosNosalMenosAuxTrans));
		Double pensionEmpleado = redondeo(pensionEmpleado(devMenosNosalMenosAuxTrans));
		Double fondoSolPensional = redondeo(fondoSolidaridadPensional(devMenosNosalMenosAuxTrans));
		Double totalDeducciones = redondeo(saludEmpleado + pensionEmpleado + fondoSolPensional);
		Double netoPagado = redondeo(totalDevengado - totalDeducciones);

		Nomina nomina = Nomina.builder().BasicoDevengado(basicoDevengado).contratoId(pEmpleadoNomina.getContratoId())
				.auxilioTransporte(auxilioTransporte).pagoNomina(pEmpleadoNomina.getPagoNomina())
				.horasExtras(horasExtras).recargos(recargos).comisiones(comisiones)
				.otrosIngresoSalarial(ingresoSalarial).otrosIngresoNoSalarial(ingresoNoSalarial)
				.totalDevengado(totalDevengado).devengadoMenosNoSalariales(devengadoMenosNoSalarial)
				.devMenosNoSalMenosAuxTrans(devMenosNosalMenosAuxTrans).saludEmpleado(saludEmpleado)
				.pensionEmpleado(pensionEmpleado).fondoSolidaridadPensional(fondoSolPensional)
				.totalDeducciones(totalDeducciones).netoPagado(netoPagado).contrato(contrato).estado("CALCULADA")
				.build();

		return nomina;
	}

	private Double basicoDevengado(EmpleadoNomina pEmpleadoNomina) {
		Integer horasTrabajadas = pEmpleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = pEmpleadoNomina.getSalarioBase();
		Double basicoDevengado = (salarioBase / 240) * horasTrabajadas;
		return basicoDevengado;
	}

	private Double salarioMinimo() {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("SALARIO MINIMO");
		Double salarioMinimo = parametroLegal.getValor();
		return salarioMinimo;
	}

	private Double auxilioTransporte(Integer pHorasTrabajadas, Double pBasicoDevengado) {
		Double auxilioTransporte = 0.0;
		Double salarioMinimo = salarioMinimo();
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("AUXILIO DE TRANSPORTE");
		Double valorAuxTransporte = parametroLegal.getValor();
		if (pBasicoDevengado <= 2 * salarioMinimo) {
			auxilioTransporte = (valorAuxTransporte / 240) * pHorasTrabajadas;
		}
		return auxilioTransporte;
	}

	private Double horasExtras(EmpleadoNomina pEmpleadoNomina) {
		RegistroHoras registroHoras = pEmpleadoNomina.getRegistroHoras();
		ParametroLegal PorcentajeExtDiurnaOrdinaria = parametrosLegales.buscarParametroPorId(5L);
		ParametroLegal PorcentajeExtNoturnaOrdinaria = parametrosLegales.buscarParametroPorId(6L);
		ParametroLegal PorcentajeExtDiurDomOfestiva = parametrosLegales.buscarParametroPorId(7L);
		ParametroLegal PorcentajeExtNoturDomOfestiva = parametrosLegales.buscarParametroPorId(8L);

		Double diurnaOrdinaria = (basicoDevengado(pEmpleadoNomina) / 240) * registroHoras.getExtrasDiurnoOrdinaro()
				* PorcentajeExtDiurnaOrdinaria.getValor();
		Double noturnaOrdinaria = (basicoDevengado(pEmpleadoNomina) / 240) * registroHoras.getExtrasNoturnoOrdinario()
				* PorcentajeExtNoturnaOrdinaria.getValor();
		Double diurnaDomFestiva = (basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getExtrasDominicalFestivoDiurno() * PorcentajeExtDiurDomOfestiva.getValor();
		Double noturnoDomFestiva = (basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getExtrasDominicalFestivoNoturno() * PorcentajeExtNoturDomOfestiva.getValor();
		Double horasExtras = diurnaOrdinaria + noturnaOrdinaria + diurnaDomFestiva + noturnoDomFestiva;

		return horasExtras;
	}

	private Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}

	private Double fondoSolidaridadPensional(Double pDevMenosNosalMenosAuxTrans) {
		Double salarioMinimo = salarioMinimo();
		Double fondoSolPensional = 0.0;
		if (pDevMenosNosalMenosAuxTrans >= 4 * salarioMinimo) {
			fondoSolPensional = pDevMenosNosalMenosAuxTrans * 0.01;
		}
		return fondoSolPensional;
	}

	private Double saludEmpleado(Double pDevMenosNosalMenosAuxTrans) {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorId(3L);
		Double saludEmpleado = pDevMenosNosalMenosAuxTrans * parametroLegal.getValor();
		return saludEmpleado;
	}

	private Double pensionEmpleado(Double pDevMenosNosalMenosAuxTrans) {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorId(4L);
		Double pensionEmpleado = pDevMenosNosalMenosAuxTrans * parametroLegal.getValor();
		return pensionEmpleado;
	}

	private Double otrosIngresoSalarial(EmpleadoNomina pEmpleadoNomina) {
		Factores factores = pEmpleadoNomina.getFactoresSalariales();
		Double ingresoSalarial = factores.getAuxilioExtra() + factores.getBonificaciones() + factores.getViaticos()
				+ factores.getOtros();
		return ingresoSalarial;
	}

	private Double otrosIngresoNoSalarial(EmpleadoNomina pEmpleadoNomina) {

		Factores factores = pEmpleadoNomina.getFactoresNoSalariales();
		Double ingresoNoSalarial = factores.getAuxilioExtra() + factores.getBonificaciones() + factores.getViaticos()
				+ factores.getOtros() + factores.getComisiones();
		return ingresoNoSalarial;
	}

	private Double recargos(EmpleadoNomina pEmpleadoNomina) {
		RegistroHoras registroHoras = pEmpleadoNomina.getRegistroHoras();
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorId(9L);
		ParametroLegal parametroLegal1 = parametrosLegales.buscarParametroPorId(10L);
		ParametroLegal parametroLegal2 = parametrosLegales.buscarParametroPorId(11L);
		Double recargoNoturno = (basicoDevengado(pEmpleadoNomina) / 240) * registroHoras.getRecargoNoturno()
				* parametroLegal.getValor();
		Double recDiurnoDomFestivo = (basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getRecargoDiurnoDominicalFestivo() * parametroLegal1.getValor();
		Double recNoturnoDomFestivo = (basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getRecargoNoturnoDominicalFestivo() * parametroLegal2.getValor();
		Double recargos = recargoNoturno + recDiurnoDomFestivo + recNoturnoDomFestivo;
		return recargos;
	}

	@Override
	public Nomina guardarNomina(EmpleadoNomina pEmpleadoNomina) {
		Nomina Vnomina = nominaDAO.validarNomina(pEmpleadoNomina.getContratoId(),
				pEmpleadoNomina.getPagoNomina().getFechaFin());
		if (Vnomina != null) {
			return null;
		}
		Pago_nomina pagoNomina = pagoNominaDAO.buscarPorPerido(pEmpleadoNomina.getPagoNomina().getFechaInicio(),
				pEmpleadoNomina.getPagoNomina().getFechaFin());
		if (pagoNomina == null) {
			pagoNomina = crearPagoNomina(pEmpleadoNomina.getPagoNomina());
		}

		RegistroHoras registroHoras = crearRegistroHoras(pEmpleadoNomina.getRegistroHoras());
		Factores factoresSalariales = crearFactores(pEmpleadoNomina.getFactoresSalariales());
		Factores factoresNoSalariales = crearFactores(pEmpleadoNomina.getFactoresNoSalariales());

		Contrato contrato = empresaClient.buscarContratoPorId(pEmpleadoNomina.getContratoId());
		registroHoras.setContrato(contrato);
		factoresSalariales.setContrato(contrato);
		factoresNoSalariales.setContrato(contrato);

		Nomina nomina = CalcularNomina(pEmpleadoNomina);
		nomina.setEstado("GUARDADA");
		nomina.setPagoNomina(pagoNomina);
		return crearNomina(nomina);
	}

	@Override
	public Nomina pagarNomina(Long pIdNomina) {
		Nomina nomina = nominaDAO.findById(pIdNomina).orElse(null);
		if (nomina == null) {
			return null;
		}
		nomina.setEstado("PAGADA");
		return crearNomina(nomina);
	}

	@Override
	public List<Pago_nomina> listarPagosNominas() {
		return pagoNominaDAO.findAll();
	}

	@Override
	public List<Nomina> listarNominas() {
		List<Nomina> listaNomina = nominaDAO.findAll().stream().map(nomina -> {
			Contrato contrato = empresaClient.buscarContratoPorId(nomina.getContratoId());
			nomina.setContrato(contrato);
			return nomina;
		}).collect(Collectors.toList());
		return listaNomina;
	}

	@Override
	public List<Factores> listarFactores() {
		List<Factores> listaFactores = factoresDAO.findAll().stream().map(factor -> {
			Contrato contrato = empresaClient.buscarContratoPorId(factor.getContratoId());
			factor.setContrato(contrato);
			return factor;
		}).collect(Collectors.toList());
		return listaFactores;
	}

	@Override
	public List<RegistroHoras> ListarRegistroHoras() {
		List<RegistroHoras> listaRegistrosNom = registroHorasDAO.findAll().stream().map(regHoras -> {
			Contrato contrato = empresaClient.buscarContratoPorId(regHoras.getContratoId());
			regHoras.setContrato(contrato);
			return regHoras;
		}).collect(Collectors.toList());
		return listaRegistrosNom;
	}

}
