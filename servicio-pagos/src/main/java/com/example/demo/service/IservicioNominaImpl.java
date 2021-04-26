package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.FactoresDAO;
import com.example.demo.dao.NominaDAO;
import com.example.demo.dao.Pago_nominaDAO;
import com.example.demo.dao.RegistroHorasDAO;
import com.example.demo.dao.SeguridadSocialDAO;
import com.example.demo.entity.Factores;
import com.example.demo.entity.Nomina;
import com.example.demo.entity.Pago_nomina;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.RegistroHoras;
import com.example.demo.entity.SeguridadSocial;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.EmpleadoNomina;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class IservicioNominaImpl implements IservicioNomina {

	final RegistroHorasDAO registroHorasDAO;

	final FactoresDAO factoresDAO;

	final Pago_nominaDAO pagoNominaDAO;

	final NominaDAO nominaDAO;

	final ServicioParametrosLegales parametrosLegales;

	final EmpresaClient empresaClient;

	@Autowired
	SeguridadSocialDAO seguridadSocialDAO;

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
		Nomina nomina = Nomina.builder().BasicoDevengado(basicoDevengado(pEmpleadoNomina))
				.auxilioTransporte(auxilioTransporte(pEmpleadoNomina)).horasExtras(horasExtras(pEmpleadoNomina))
				.recargos(recargos(pEmpleadoNomina)).comisiones(comisiones(pEmpleadoNomina))
				.otrosIngresoSalarial(otrosIngresoSalarial(pEmpleadoNomina))
				.otrosIngresoNoSalarial(otrosIngresoNoSalarial(pEmpleadoNomina))
				.totalDevengado(totalDevengado(pEmpleadoNomina))
				.devengadoMenosNoSalariales(devengadoMenosNoSalariales(pEmpleadoNomina))
				.devMenosNoSalMenosAuxTrans(devMenosNoSalMenosAuxTrans(pEmpleadoNomina))
				.saludEmpleado(saludEmpleado(pEmpleadoNomina)).pensionEmpleado(pensionEmpleado(pEmpleadoNomina))
				.fondoSolidaridadPensional(fondoSolidaridadPensional(pEmpleadoNomina))
				.totalDeducciones(totalDeducciones(pEmpleadoNomina)).netoPagado(netoPagado(pEmpleadoNomina))
				.estado("CALCULADA").contrato(contrato).contratoId(pEmpleadoNomina.getContratoId())
				.pagoNomina(pEmpleadoNomina.getPagoNomina()).build();
		return nomina;
	}

	public Double basicoDevengado(EmpleadoNomina pEmpleadoNomina) {
		Integer horasTrabajadas = pEmpleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = pEmpleadoNomina.getSalarioBase();
		return redondeo((salarioBase / 240) * horasTrabajadas);
	}

	public Double salarioMinimo() {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("SALARIO MINIMO");
		Double salarioMinimo = parametroLegal.getValor();
		return redondeo(salarioMinimo);
	}

	public Double auxilioTransporte(EmpleadoNomina pEmpleadoNomina) {
		Double auxilioTransporte = 0.0;
		Integer horasTrabajadas = pEmpleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioMinimo = salarioMinimo();
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("AUXILIO DE TRANSPORTE");
		Double valorAuxilioTransporte = parametroLegal.getValor();
		Double basicoDevengado = basicoDevengado(pEmpleadoNomina);
		if (basicoDevengado <= 2 * salarioMinimo) {
			auxilioTransporte = (valorAuxilioTransporte / 240) * horasTrabajadas;
		}
		return redondeo(auxilioTransporte);
	}

	public Double horasExtras(EmpleadoNomina pEmpleadoNomina) {
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
		return redondeo(horasExtras);
	}

	public Double recargos(EmpleadoNomina pEmpleadoNomina) {
		Double recargos = 0.0;
		RegistroHoras registroHoras = pEmpleadoNomina.getRegistroHoras();
		ParametroLegal pRecargoNoturno = parametrosLegales.buscarParametroPorId(9L);
		ParametroLegal pRecargoDiurDomOfestivo = parametrosLegales.buscarParametroPorId(10L);
		ParametroLegal pRecagoNoturDomOfestivo = parametrosLegales.buscarParametroPorId(11L);

		Double recargoNoturno = ((basicoDevengado(pEmpleadoNomina) / 240) * registroHoras.getRecargoNoturno()
				* pRecargoNoturno.getValor());
		Double recargoDiurDomOfestivo = ((basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getRecargoDiurnoDominicalFestivo() * pRecargoDiurDomOfestivo.getValor());
		Double recargoNoturDomOfestivo = ((basicoDevengado(pEmpleadoNomina) / 240)
				* registroHoras.getRecargoNoturnoDominicalFestivo() * pRecagoNoturDomOfestivo.getValor());
		recargos = recargoNoturno + recargoDiurDomOfestivo + recargoNoturDomOfestivo;
		return redondeo(recargos);
	}

	public Double comisiones(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(pEmpleadoNomina.getFactoresSalariales().getComisiones());
	}

	public Double otrosIngresoSalarial(EmpleadoNomina pEmpleadoNomina) {
		Double ingresoSalarial = 0.0;
		Factores factoresSalariales = pEmpleadoNomina.getFactoresSalariales();
		ingresoSalarial = factoresSalariales.getAuxilioExtra() + factoresSalariales.getBonificaciones()
				+ factoresSalariales.getComisiones() + factoresSalariales.getOtros() + factoresSalariales.getViaticos();
		return redondeo(ingresoSalarial);
	}

	public Double otrosIngresoNoSalarial(EmpleadoNomina pEmpleadoNomina) {
		Double ingresoNoSalarial = 0.0;
		Factores factoresNoSalariales = pEmpleadoNomina.getFactoresSalariales();
		ingresoNoSalarial = factoresNoSalariales.getAuxilioExtra() + factoresNoSalariales.getBonificaciones()
				+ factoresNoSalariales.getComisiones() + factoresNoSalariales.getOtros()
				+ factoresNoSalariales.getViaticos();
		return redondeo(ingresoNoSalarial);
	}

	public Double totalDevengado(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(basicoDevengado(pEmpleadoNomina) + auxilioTransporte(pEmpleadoNomina)
				+ horasExtras(pEmpleadoNomina) + recargos(pEmpleadoNomina) + comisiones(pEmpleadoNomina)
				+ otrosIngresoSalarial(pEmpleadoNomina) + otrosIngresoNoSalarial(pEmpleadoNomina));
	}

	public Double devengadoMenosNoSalariales(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(totalDevengado(pEmpleadoNomina) - otrosIngresoNoSalarial(pEmpleadoNomina));
	}

	public Double devMenosNoSalMenosAuxTrans(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(totalDevengado(pEmpleadoNomina) - otrosIngresoNoSalarial(pEmpleadoNomina)
				- auxilioTransporte(pEmpleadoNomina));
	}

	public Double saludEmpleado(EmpleadoNomina pEmpleadoNomina) {
		ParametroLegal porcentajeSaludEmpleado = parametrosLegales.buscarParametroPorId(3L);
		return redondeo(devMenosNoSalMenosAuxTrans(pEmpleadoNomina) * porcentajeSaludEmpleado.getValor());
	}

	public Double pensionEmpleado(EmpleadoNomina pEmpleadoNomina) {
		ParametroLegal porcentajePensionEmpleado = parametrosLegales.buscarParametroPorId(4L);
		return redondeo(devMenosNoSalMenosAuxTrans(pEmpleadoNomina) * porcentajePensionEmpleado.getValor());
	}

	public Double fondoSolidaridadPensional(EmpleadoNomina pEmpleadoNomina) {
		Double fondoSolPensional = 0.0;
		Double salarioMinimo = salarioMinimo();
		if (devMenosNoSalMenosAuxTrans(pEmpleadoNomina) >= 4 * salarioMinimo) {
			fondoSolPensional = devMenosNoSalMenosAuxTrans(pEmpleadoNomina) * 0.01;
		}
		return redondeo(fondoSolPensional);
	}

	public Double totalDeducciones(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(saludEmpleado(pEmpleadoNomina) + pensionEmpleado(pEmpleadoNomina)
				+ fondoSolidaridadPensional(pEmpleadoNomina));
	}

	public Double netoPagado(EmpleadoNomina pEmpleadoNomina) {
		return redondeo(totalDevengado(pEmpleadoNomina) - totalDeducciones(pEmpleadoNomina));
	}

	public Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}

	@Override
	public Nomina guardarNomina(EmpleadoNomina pEmpleadoNomina) {
		Nomina Vnomina = nominaDAO.validarNomina(pEmpleadoNomina.getContratoId(),
				pEmpleadoNomina.getPagoNomina().getFechaFin());
		if (Vnomina != null) {
			return null;
		}
		Pago_nomina pagoNomina = pagoNominaDAO.buscarPorPeriodo(pEmpleadoNomina.getPagoNomina().getFechaInicio(),
				pEmpleadoNomina.getPagoNomina().getFechaFin());
		if (pagoNomina == null) {
			pagoNomina = crearPagoNomina(pEmpleadoNomina.getPagoNomina());
		}
		crearRegistroHoras(pEmpleadoNomina.getRegistroHoras());
		crearFactores(pEmpleadoNomina.getFactoresSalariales());
		crearFactores(pEmpleadoNomina.getFactoresNoSalariales());

		Nomina nomina = CalcularNomina(pEmpleadoNomina);
		nomina.setEstado("GUARDADA");
		nomina.setPagoNomina(pagoNomina);
		SeguridadSocial seguridadSocial = SeguridadSocial.builder()
				.ARL(nomina.getContrato().getTarifaArl().getCotizacion() * nomina.getDevMenosNoSalMenosAuxTrans())
				.nomina(nomina).build();
		seguridadSocialDAO.save(seguridadSocial);
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

	@Override
	public SeguridadSocial crearSeguridadSocial(SeguridadSocial pSeguridadSocial) {
		return seguridadSocialDAO.save(pSeguridadSocial);
	}

	@Override
	public List<SeguridadSocial> ListaSeguridadSocial() {
		List<SeguridadSocial> listaSeguridadSocial = seguridadSocialDAO.findAll().stream().map(seguridadSocial -> {
			Contrato contrato = empresaClient.buscarContratoPorId(seguridadSocial.getNomina().getContratoId());
			seguridadSocial.getNomina().setContrato(contrato);
			return seguridadSocial;
		}).collect(Collectors.toList());
		return listaSeguridadSocial;
	}

	@Override
	public SeguridadSocial buscarSeguridadSocial(Long pContratoId) {
		SeguridadSocial seguridadSocial = seguridadSocialDAO.obtenerSeguridadSocialEmpleado(pContratoId);
		Contrato contrato = empresaClient.buscarContratoPorId(seguridadSocial.getNomina().getContratoId());
		seguridadSocial.getNomina().setContrato(contrato);
		return seguridadSocial;
	}

}
