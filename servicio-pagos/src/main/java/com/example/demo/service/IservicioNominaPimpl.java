package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.DetalleNominaDAO;
import com.example.demo.dao.FactoresNoSalarialesDAO;
import com.example.demo.dao.FactoresSalarialesDAO;
import com.example.demo.dao.NominaPDAO;
import com.example.demo.dao.ParametrosLegalesDAO;
import com.example.demo.dao.PrestacionesSocialesDAO;
import com.example.demo.dao.RegistroHorasPDAO;
import com.example.demo.dao.SeguridadSocialDAO;
import com.example.demo.entity.DetalleNomina;
import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.PrestacionesSociales;
import com.example.demo.entity.RegistroHorasP;
import com.example.demo.entity.SeguridadSocial;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.DetalleNominaPk;
import com.example.demo.model.EmpleadoNominaP;
import com.example.demo.model.Nomina;

@Service
public class IservicioNominaPimpl implements IservicioNominaP {

	//private EmpleadoNominaP empleadoNomina;

	@Autowired
	private ParametrosLegalesDAO parametrosDao;

	@Autowired
	private NominaPDAO nominaDao;

	@Autowired
	private FactoresSalarialesDAO factoresSalarialesDao;

	@Autowired
	private FactoresNoSalarialesDAO factoresNOsalarialesDao;

	@Autowired
	private RegistroHorasPDAO registroHorasDao;

	@Autowired
	private DetalleNominaDAO detalleNominaDao;

	@Autowired
	private SeguridadSocialDAO seguridadSocialDao;
	
	@Autowired
	private PrestacionesSocialesDAO prestacionesDao;

	@Autowired
	private EmpresaClient empresaClient;

	@Override
	public DetalleNomina generarDetalleNomina(EmpleadoNominaP pEmpleadoNomina) {
		Contrato contrato = empresaClient.buscarContratoPorId(pEmpleadoNomina.getContratoId());
		DetalleNominaPk detalleNominaPk = DetalleNominaPk.builder().contratoId(pEmpleadoNomina.getContratoId())
				.nomina(pEmpleadoNomina.getNomina()).build();

		DetalleNomina detalleNomina = DetalleNomina.builder().detalleNominaPk(detalleNominaPk)
				.BasicoDevengado(basicoDevengado(pEmpleadoNomina)).contrato(contrato).BasicoDevengado(basicoDevengado(pEmpleadoNomina))
				.auxilioTransporte(auxilioTransporte(pEmpleadoNomina)).horasExtras(horasExtras(pEmpleadoNomina)).recargos(recargos(pEmpleadoNomina))
				.comisiones(comisiones(pEmpleadoNomina)).otrosIngresoSalarial(otrosIngresoSalarial(pEmpleadoNomina))
				.otrosIngresoNoSalarial(otrosIngresoNoSalarial(pEmpleadoNomina)).totalDevengado(totalDevengado(pEmpleadoNomina))
				.devengadoMenosNoSalariales(devengadoMenosNoSalariales(pEmpleadoNomina))
				.devMenosNoSalMenosAuxTrans(devMenosNoSalMenosAuxTrans(pEmpleadoNomina)).saludEmpleado(saludEmpleado(pEmpleadoNomina))
				.pensionEmpleado(pensionEmpleado(pEmpleadoNomina)).fondoSolidaridadPensional(fondoSolidaridadPensional(pEmpleadoNomina))
				.totalDeducciones(totalDeducciones(pEmpleadoNomina)).netoPagado(netoPagado(pEmpleadoNomina)).estado("POR PAGAR")
				.factoresSalariales(pEmpleadoNomina.getFactoresSalariales())
				.factoresNoSalariales(pEmpleadoNomina.getFactoresNoSalariales())
				.registroHoras(pEmpleadoNomina.getRegistroHoras())
				.nomina(fijarValoresNominaDTO(pEmpleadoNomina.getNomina())).build();
		return detalleNomina;
	}
	
	public Nomina fijarValoresNominaDTO(NominaP pNomina) 
	{
		Nomina nominaDTO = Nomina.builder()
				.fechaInicio(pNomina.getFechaInicio())
				.fechaFin(pNomina.getFechaFin())
				.detalle(pNomina.getDetalle()).build();
		return nominaDTO;
	}

	public Double basicoDevengado(EmpleadoNominaP empleadoNomina) {
		Integer horasTrabajadas = empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = empleadoNomina.getSalarioBase();
		return redondeo((salarioBase / 240) * horasTrabajadas);
	}

	public Double auxilioTransporte(EmpleadoNominaP empleadoNomina) {
		Double auxilioTransporte = 0.0;
		Integer horasTrabajadas = empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioMinimo = salarioMinimo();
		ParametroLegal parametroLegal = parametrosDao.findByNombreIgnoreCase("AUXILIO DE TRANSPORTE");
		Double valorAuxilioTransporte = parametroLegal.getValor();
		Double basicoDevengado = basicoDevengado(empleadoNomina);
		if (basicoDevengado <= 2 * salarioMinimo) {
			auxilioTransporte = (valorAuxilioTransporte / 240) * horasTrabajadas;
		}
		return redondeo(auxilioTransporte);
	}

	public Double horasExtras(EmpleadoNominaP empleadoNomina) {
		RegistroHorasP registroHoras = empleadoNomina.getRegistroHoras();
		ParametroLegal PorcentajeExtDiurnaOrdinaria = parametrosDao.findById(5L).orElse(null);
		ParametroLegal PorcentajeExtNoturnaOrdinaria = parametrosDao.findById(6L).orElse(null);
		ParametroLegal PorcentajeExtDiurDomOfestiva = parametrosDao.findById(7L).orElse(null);
		ParametroLegal PorcentajeExtNoturDomOfestiva = parametrosDao.findById(8L).orElse(null);

		Double diurnaOrdinaria = (basicoDevengado(empleadoNomina) / 240) * registroHoras.getExtrasDiurnoOrdinaro()
				* PorcentajeExtDiurnaOrdinaria.getValor();
		Double noturnaOrdinaria = (basicoDevengado(empleadoNomina) / 240) * registroHoras.getExtrasNoturnoOrdinario()
				* PorcentajeExtNoturnaOrdinaria.getValor();
		Double diurnaDomFestiva = (basicoDevengado(empleadoNomina) / 240) * registroHoras.getExtrasDominicalFestivoDiurno()
				* PorcentajeExtDiurDomOfestiva.getValor();
		Double noturnoDomFestiva = (basicoDevengado(empleadoNomina) / 240) * registroHoras.getExtrasDominicalFestivoNoturno()
				* PorcentajeExtNoturDomOfestiva.getValor();
		Double horasExtras = diurnaOrdinaria + noturnaOrdinaria + diurnaDomFestiva + noturnoDomFestiva;
		return redondeo(horasExtras);
	}

	public Double recargos(EmpleadoNominaP empleadoNomina) {
		Double recargos = 0.0;
		RegistroHorasP registroHoras = empleadoNomina.getRegistroHoras();
		ParametroLegal pRecargoNoturno = parametrosDao.findById(9L).orElse(null);
		ParametroLegal pRecargoDiurDomOfestivo = parametrosDao.findById(10L).orElse(null);
		ParametroLegal pRecagoNoturDomOfestivo = parametrosDao.findById(11L).orElse(null);

		Double recargoNoturno = ((basicoDevengado(empleadoNomina) / 240) * registroHoras.getRecargoNoturno()
				* pRecargoNoturno.getValor());
		Double recargoDiurDomOfestivo = ((basicoDevengado(empleadoNomina) / 240) * registroHoras.getRecargoDiurnoDominicalFestivo()
				* pRecargoDiurDomOfestivo.getValor());
		Double recargoNoturDomOfestivo = ((basicoDevengado(empleadoNomina) / 240) * registroHoras.getRecargoNoturnoDominicalFestivo()
				* pRecagoNoturDomOfestivo.getValor());
		recargos = recargoNoturno + recargoDiurDomOfestivo + recargoNoturDomOfestivo;
		return redondeo(recargos);
	}

	public Double comisiones(EmpleadoNominaP empleadoNomina) {
		return redondeo(empleadoNomina.getFactoresSalariales().getComisiones());
	}

	public Double otrosIngresoSalarial(EmpleadoNominaP empleadoNomina) {
		Double ingresoSalarial = 0.0;
		FactoresSalariales factoresSalariales = empleadoNomina.getFactoresSalariales();
		ingresoSalarial = factoresSalariales.getAuxilioExtra() + factoresSalariales.getBonificaciones()
				+ factoresSalariales.getComisiones() + factoresSalariales.getOtros() + factoresSalariales.getViaticos();
		return redondeo(ingresoSalarial);
	}

	public Double otrosIngresoNoSalarial(EmpleadoNominaP empleadoNomina) {
		Double ingresoNoSalarial = 0.0;
		FactoresNoSalariales factoresNoSalariales = empleadoNomina.getFactoresNoSalariales();
		ingresoNoSalarial = factoresNoSalariales.getAuxilioExtra() + factoresNoSalariales.getBonificaciones()
				+ factoresNoSalariales.getComisiones() + factoresNoSalariales.getOtros()
				+ factoresNoSalariales.getViaticos();
		return redondeo(ingresoNoSalarial);
	}

	public Double totalDevengado(EmpleadoNominaP empleadoNomina) {
		return redondeo(basicoDevengado(empleadoNomina) + auxilioTransporte(empleadoNomina) + horasExtras(empleadoNomina) + recargos(empleadoNomina) + comisiones(empleadoNomina)
				+ otrosIngresoSalarial(empleadoNomina) + otrosIngresoNoSalarial(empleadoNomina));
	}

	public Double devengadoMenosNoSalariales(EmpleadoNominaP empleadoNomina) {
		return redondeo(totalDevengado(empleadoNomina) - otrosIngresoNoSalarial(empleadoNomina));
	}

	public Double devMenosNoSalMenosAuxTrans(EmpleadoNominaP empleadoNomina) {
		return redondeo(totalDevengado(empleadoNomina) - otrosIngresoNoSalarial(empleadoNomina) - auxilioTransporte(empleadoNomina));
	}

	public Double saludEmpleado(EmpleadoNominaP empleadoNomina) {
		ParametroLegal porcentajeSaludEmpleado = parametrosDao.findById(3L).orElse(null);
		return redondeo(devMenosNoSalMenosAuxTrans(empleadoNomina) * porcentajeSaludEmpleado.getValor());
	}

	public Double pensionEmpleado(EmpleadoNominaP empleadoNomina) {
		ParametroLegal porcentajePensionEmpleado = parametrosDao.findById(4L).orElse(null);
		return redondeo(devMenosNoSalMenosAuxTrans(empleadoNomina) * porcentajePensionEmpleado.getValor());
	}

	public Double fondoSolidaridadPensional(EmpleadoNominaP empleadoNomina) {
		Double fondoSolPensional = 0.0;
		Double salarioMinimo = salarioMinimo();
		if (devMenosNoSalMenosAuxTrans(empleadoNomina) >= 4 * salarioMinimo) {
			fondoSolPensional = devMenosNoSalMenosAuxTrans(empleadoNomina) * 0.01;
		}
		return redondeo(fondoSolPensional);
	}

	public Double totalDeducciones(EmpleadoNominaP empleadoNomina) {
		return redondeo(saludEmpleado(empleadoNomina) + pensionEmpleado(empleadoNomina) + fondoSolidaridadPensional(empleadoNomina));
	}

	public Double netoPagado(EmpleadoNominaP empleadoNomina) {
		return redondeo(totalDevengado(empleadoNomina) - totalDeducciones(empleadoNomina));
	}

	public Double salarioMinimo() {
		ParametroLegal parametroLegal = parametrosDao.findByNombreIgnoreCase("SALARIO MINIMO");
		Double salarioMinimo = parametroLegal.getValor();
		return redondeo(salarioMinimo);
	}

	public Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}

	@Override
	public DetalleNomina guardarDetalleNomina(EmpleadoNominaP empleadoNomina) {
		DetalleNomina detalleNomina = this.generarDetalleNomina(empleadoNomina);

		if (validarExistenciaNomina(empleadoNomina)) {
			return null;
		}
		NominaP nomina = crearNomina(validarPeriodoNominaEmpleado(empleadoNomina));
		detalleNomina.setNomina(fijarValoresNominaDTO(nomina));
		detalleNomina.getDetalleNominaPk().setNomina(nomina);
		detalleNomina.setSeguridadSocial(generarSeguridadSocial(detalleNomina));
		detalleNomina.setPrestacionesSociales(generarPrestacionesSociales(detalleNomina));
		DetalleNomina detalleNominaGuardada = detalleNominaDao.save(detalleNomina);
		detalleNominaGuardada.setContrato(detalleNomina.getContrato());
		return detalleNominaGuardada;
	}

	private Boolean validarExistenciaNomina(EmpleadoNominaP empleadoNomina) {
		boolean bandera = false;
		NominaP validarNomina = nominaDao.validarPeriodoNomina(empleadoNomina.getContratoId(),
				empleadoNomina.getNomina().getFechaFin());
		if (validarNomina != null) {
			bandera = true;
		}
		return bandera;
	}

	private NominaP validarPeriodoNominaEmpleado(EmpleadoNominaP empleadoNomina) {
		Date fechaInicio = empleadoNomina.getNomina().getFechaInicio();
		Date fechaFin = empleadoNomina.getNomina().getFechaFin();
		NominaP validarPeriodoNomina = empleadoNomina.getNomina();
		if (nominaDao.validaNomina(fechaInicio, fechaFin) != null) {
			validarPeriodoNomina = nominaDao.validaNomina(fechaInicio, fechaFin);
		}
		return validarPeriodoNomina;
	}

	private SeguridadSocial generarSeguridadSocial(DetalleNomina pDetalleNomina) {
		Double arl = pDetalleNomina.getContrato().getTarifaArl().getCotizacion()
				* pDetalleNomina.getDevMenosNoSalMenosAuxTrans();
		SeguridadSocial seguridadSocial = SeguridadSocial.builder()
				.detalleNomina(pDetalleNomina).estado("POR PAGAR").ARL(arl).build();
		return seguridadSocialDao.save(seguridadSocial);
	}
	private PrestacionesSociales generarPrestacionesSociales(DetalleNomina pDetalleNomina) {
		PrestacionesSociales prestacionesSociales = PrestacionesSociales.builder()
				.detalleNomina(pDetalleNomina).estado("POR PAGAR").build();
		return prestacionesDao.save(prestacionesSociales);
	}

	@Override
	public List<NominaP> listarPeridoNominas() {
		List<NominaP> listaNominas = nominaDao
				.findAll().stream().map(nominas ->{
					nominas.getListaDetalleNomina().forEach(detalleNomina ->{
						Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
						detalleNomina.setContrato(contrato);
						Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
						detalleNomina.setNomina(nomina);
					});
					return nominas;
				}).collect(Collectors.toList());
		return listaNominas;
	}

	@Override
	public List<DetalleNomina> listarDetallesNominas() {

		List<DetalleNomina> listaDetallesNominas = detalleNominaDao.findAll().stream().map(detalleNomina -> {
			Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
			Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
			detalleNomina.setContrato(contrato);
			detalleNomina.setNomina(nomina);
			return detalleNomina;
		}).collect(Collectors.toList());

		return listaDetallesNominas;
	}

	@Override
	public List<FactoresSalariales> listarFactoresSalariales() {
		List<FactoresSalariales> listaFactores = factoresSalarialesDao.findAll().stream().map(factorSalarial -> {
			Contrato contrato = empresaClient
					.buscarContratoPorId(factorSalarial.getDetalleNomina().getDetalleNominaPk().getContratoId());
			factorSalarial.getDetalleNomina().setContrato(contrato);
			return factorSalarial;
		}).collect(Collectors.toList());
		return listaFactores;
	}

	@Override
	public List<FactoresNoSalariales> listarFactoresNoSalariales() {
		List<FactoresNoSalariales> listFacSalariales = factoresNOsalarialesDao.findAll().stream()
				.map(factorNoSalarial -> {
					Contrato contrato = empresaClient.buscarContratoPorId(
							factorNoSalarial.getDetalleNomina().getDetalleNominaPk().getContratoId());
					factorNoSalarial.getDetalleNomina().setContrato(contrato);
					return factorNoSalarial;
				}).collect(Collectors.toList());
		return listFacSalariales;
	}

	@Override
	public List<RegistroHorasP> ListarRegistroHoras() {
		List<RegistroHorasP> listRegistroHoras = registroHorasDao.findAll().stream().map(regHoras -> {
			Contrato contrato = empresaClient
					.buscarContratoPorId(regHoras.getDetalleNomina().getDetalleNominaPk().getContratoId());
			regHoras.getDetalleNomina().setContrato(contrato);
			return regHoras;
		}).collect(Collectors.toList());
		return listRegistroHoras;
	}

	@Override
	public List<DetalleNomina> listarDetallesNominaPorPeriodo(String pFechaInicio, String pFechaFin) {
		Date fechaInicio = ParseFecha(pFechaInicio);
		Date fechaFin = ParseFecha(pFechaFin);
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.ListarDetallesNomPorPeriodo(fechaInicio, fechaFin).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		return listaDetalles;
	}
	
	@Override
	public List<DetalleNomina> listarDetallesNominaPorContrato(Long pContratoId) {
		List<DetalleNomina> listaDetalleNomina = detalleNominaDao.listarDetallesNomPorContrato(pContratoId).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		return listaDetalleNomina;
	}
	
	@Override
	public List<DetalleNomina> listarDetallesNominaPorEstado(String pFechaInicio, String pFechaFin, String pEstado) {
		Date fechaInicio = ParseFecha(pFechaInicio);
		Date fechaFin = ParseFecha(pFechaFin);
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.listarDetallesNomPorEstado(fechaInicio, fechaFin, pEstado).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		return listaDetalles;
		
	}
	
	@Override
	public List<DetalleNomina> listarDetNominaPorContratoYestado(Long pContratoID, String pEstado) {
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.listDetNominaPorContratoYestado(pContratoID, pEstado).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		
		return listaDetalles;
	}
	
	@Override
	public List<DetalleNomina> listarDetalleNominaPorIdNomina(Long pNominaId) {
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.listDetNominaPorIdNomina(pNominaId).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		
		return listaDetalles;
	}

	@Override
	public List<DetalleNomina> listDetNominaPorIdNominaYestado(Long pNominaId, String pEstado) {
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.listDetNominaPorIdNominaYestado(pNominaId, pEstado).stream()
				.map(detalleNomina ->{
					Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					detalleNomina.setNomina(nomina);
					return detalleNomina;
				}).collect(Collectors.toList());
		
		return listaDetalles;
	}

	private Date ParseFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return fechaDate;
	}

	@Override
	public DetalleNomina pagarDetalleNominaEmpleado(Long pContratoId, Long pNominaId) {
		DetalleNomina detalleNomina = detalleNominaDao.buscarDetalleNomina(pContratoId, pContratoId);
		if (detalleNomina == null) {
			return null;
		}
		Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
		Contrato contrato = empresaClient.buscarContratoPorId(pContratoId);
		detalleNomina.setEstado("PAGADO");
		detalleNomina.setContrato(contrato);
		detalleNomina.setNomina(nomina);
		return detalleNominaDao.save(detalleNomina);
	}

	@Override
	public NominaP crearNomina(NominaP pNomina) {
		pNomina.setEstado("GUARDADA");
		return nominaDao.save(pNomina);
	}

	@Override
	public List<SeguridadSocial> ListaSeguridadSocial() {
		List<SeguridadSocial> listaSeguridadSocial = seguridadSocialDao.findAll().stream().map(segSocial->{
			Contrato contrato = empresaClient.buscarContratoPorId(segSocial.getDetalleNomina().getDetalleNominaPk().getContratoId());
			segSocial.getDetalleNomina().setContrato(contrato);
			return segSocial;
		}).collect(Collectors.toList());
		return listaSeguridadSocial;
	}

	@Override
	public DetalleNomina buscarDetalleNomina(Long pContratoId, Long pNominaId) {
		Contrato contrato = empresaClient.buscarContratoPorId(pContratoId);
		DetalleNomina detalleNomina = detalleNominaDao.buscarDetalleNomina(pContratoId, pNominaId);
		if(detalleNomina !=null) {
			Nomina nomina = fijarValoresNominaDTO(detalleNomina.getDetalleNominaPk().getNomina());
			detalleNomina.setNomina(nomina);
			detalleNomina.setContrato(contrato);
		}
		
		return detalleNomina;
	}

}
