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
import com.example.demo.dao.RegistroHorasPDAO;
import com.example.demo.dao.SeguridadSocialDAO;
import com.example.demo.entity.DetalleNomina;
import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.RegistroHorasP;
import com.example.demo.entity.SeguridadSocial;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.DetalleNominaPk;
import com.example.demo.model.EmpleadoNominaP;
import com.example.demo.model.Nomina;

@Service
public class IservicioNominaPimpl implements IservicioNominaP {

	private EmpleadoNominaP empleadoNomina;

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
	private EmpresaClient empresaClient;

	@Override
	public DetalleNomina generarDetalleNomina(EmpleadoNominaP pEmpleadoNomina) {
		this.empleadoNomina = pEmpleadoNomina;
		Contrato contrato = empresaClient.buscarContratoPorId(pEmpleadoNomina.getContratoId());
		DetalleNominaPk detalleNominaPk = DetalleNominaPk.builder().contratoId(empleadoNomina.getContratoId())
				.nomina(empleadoNomina.getNomina()).build();

		DetalleNomina detalleNomina = DetalleNomina.builder().detalleNominaPk(detalleNominaPk)
				.BasicoDevengado(basicoDevengado()).contrato(contrato).BasicoDevengado(basicoDevengado())
				.auxilioTransporte(auxilioTransporte()).horasExtras(horasExtras()).recargos(recargos())
				.comisiones(comisiones()).otrosIngresoSalarial(otrosIngresoSalarial())
				.otrosIngresoNoSalarial(otrosIngresoNoSalarial()).totalDevengado(totalDevengado())
				.devengadoMenosNoSalariales(devengadoMenosNoSalariales())
				.devMenosNoSalMenosAuxTrans(devMenosNoSalMenosAuxTrans()).saludEmpleado(saludEmpleado())
				.pensionEmpleado(pensionEmpleado()).fondoSolidaridadPensional(fondoSolidaridadPensional())
				.totalDeducciones(totalDeducciones()).netoPagado(netoPagado()).estado("POR PAGAR")
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

	public Double basicoDevengado() {
		Integer horasTrabajadas = this.empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = this.empleadoNomina.getSalarioBase();
		return redondeo((salarioBase / 240) * horasTrabajadas);
	}

	public Double auxilioTransporte() {
		Double auxilioTransporte = 0.0;
		Integer horasTrabajadas = this.empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioMinimo = salarioMinimo();
		ParametroLegal parametroLegal = parametrosDao.findByNombreIgnoreCase("AUXILIO DE TRANSPORTE");
		Double valorAuxilioTransporte = parametroLegal.getValor();
		Double basicoDevengado = basicoDevengado();
		if (basicoDevengado <= 2 * salarioMinimo) {
			auxilioTransporte = (valorAuxilioTransporte / 240) * horasTrabajadas;
		}
		return redondeo(auxilioTransporte);
	}

	public Double horasExtras() {
		RegistroHorasP registroHoras = this.empleadoNomina.getRegistroHoras();
		ParametroLegal PorcentajeExtDiurnaOrdinaria = parametrosDao.findById(5L).orElse(null);
		ParametroLegal PorcentajeExtNoturnaOrdinaria = parametrosDao.findById(6L).orElse(null);
		ParametroLegal PorcentajeExtDiurDomOfestiva = parametrosDao.findById(7L).orElse(null);
		ParametroLegal PorcentajeExtNoturDomOfestiva = parametrosDao.findById(8L).orElse(null);

		Double diurnaOrdinaria = (basicoDevengado() / 240) * registroHoras.getExtrasDiurnoOrdinaro()
				* PorcentajeExtDiurnaOrdinaria.getValor();
		Double noturnaOrdinaria = (basicoDevengado() / 240) * registroHoras.getExtrasNoturnoOrdinario()
				* PorcentajeExtNoturnaOrdinaria.getValor();
		Double diurnaDomFestiva = (basicoDevengado() / 240) * registroHoras.getExtrasDominicalFestivoDiurno()
				* PorcentajeExtDiurDomOfestiva.getValor();
		Double noturnoDomFestiva = (basicoDevengado() / 240) * registroHoras.getExtrasDominicalFestivoNoturno()
				* PorcentajeExtNoturDomOfestiva.getValor();
		Double horasExtras = diurnaOrdinaria + noturnaOrdinaria + diurnaDomFestiva + noturnoDomFestiva;
		return redondeo(horasExtras);
	}

	public Double recargos() {
		Double recargos = 0.0;
		RegistroHorasP registroHoras = this.empleadoNomina.getRegistroHoras();
		ParametroLegal pRecargoNoturno = parametrosDao.findById(9L).orElse(null);
		ParametroLegal pRecargoDiurDomOfestivo = parametrosDao.findById(10L).orElse(null);
		ParametroLegal pRecagoNoturDomOfestivo = parametrosDao.findById(11L).orElse(null);

		Double recargoNoturno = ((basicoDevengado() / 240) * registroHoras.getRecargoNoturno()
				* pRecargoNoturno.getValor());
		Double recargoDiurDomOfestivo = ((basicoDevengado() / 240) * registroHoras.getRecargoDiurnoDominicalFestivo()
				* pRecargoDiurDomOfestivo.getValor());
		Double recargoNoturDomOfestivo = ((basicoDevengado() / 240) * registroHoras.getRecargoNoturnoDominicalFestivo()
				* pRecagoNoturDomOfestivo.getValor());
		recargos = recargoNoturno + recargoDiurDomOfestivo + recargoNoturDomOfestivo;
		return redondeo(recargos);
	}

	public Double comisiones() {
		return redondeo(this.empleadoNomina.getFactoresSalariales().getComisiones());
	}

	public Double otrosIngresoSalarial() {
		Double ingresoSalarial = 0.0;
		FactoresSalariales factoresSalariales = this.empleadoNomina.getFactoresSalariales();
		ingresoSalarial = factoresSalariales.getAuxilioExtra() + factoresSalariales.getBonificaciones()
				+ factoresSalariales.getComisiones() + factoresSalariales.getOtros() + factoresSalariales.getViaticos();
		return redondeo(ingresoSalarial);
	}

	public Double otrosIngresoNoSalarial() {
		Double ingresoNoSalarial = 0.0;
		FactoresNoSalariales factoresNoSalariales = this.empleadoNomina.getFactoresNoSalariales();
		ingresoNoSalarial = factoresNoSalariales.getAuxilioExtra() + factoresNoSalariales.getBonificaciones()
				+ factoresNoSalariales.getComisiones() + factoresNoSalariales.getOtros()
				+ factoresNoSalariales.getViaticos();
		return redondeo(ingresoNoSalarial);
	}

	public Double totalDevengado() {
		return redondeo(basicoDevengado() + auxilioTransporte() + horasExtras() + recargos() + comisiones()
				+ otrosIngresoSalarial() + otrosIngresoNoSalarial());
	}

	public Double devengadoMenosNoSalariales() {
		return redondeo(totalDevengado() - otrosIngresoNoSalarial());
	}

	public Double devMenosNoSalMenosAuxTrans() {
		return redondeo(totalDevengado() - otrosIngresoNoSalarial() - auxilioTransporte());
	}

	public Double saludEmpleado() {
		ParametroLegal porcentajeSaludEmpleado = parametrosDao.findById(3L).orElse(null);
		return redondeo(devMenosNoSalMenosAuxTrans() * porcentajeSaludEmpleado.getValor());
	}

	public Double pensionEmpleado() {
		ParametroLegal porcentajePensionEmpleado = parametrosDao.findById(4L).orElse(null);
		return redondeo(devMenosNoSalMenosAuxTrans() * porcentajePensionEmpleado.getValor());
	}

	public Double fondoSolidaridadPensional() {
		Double fondoSolPensional = 0.0;
		Double salarioMinimo = salarioMinimo();
		if (devMenosNoSalMenosAuxTrans() >= 4 * salarioMinimo) {
			fondoSolPensional = devMenosNoSalMenosAuxTrans() * 0.01;
		}
		return redondeo(fondoSolPensional);
	}

	public Double totalDeducciones() {
		return redondeo(saludEmpleado() + pensionEmpleado() + fondoSolidaridadPensional());
	}

	public Double netoPagado() {
		return redondeo(totalDevengado() - totalDeducciones());
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
	public DetalleNomina guardarDetalleNomina(DetalleNomina pDetalleNomina) {

		if (validarExistenciaNomina()) {
			return null;
		}
		NominaP nomina = crearNomina(validarPeriodoNominaEmpleado());
		pDetalleNomina.setNomina(fijarValoresNominaDTO(nomina));
		pDetalleNomina.getDetalleNominaPk().setNomina(nomina);
		pDetalleNomina.setSeguridadSocial(generarSeguridadSocial(pDetalleNomina));
		DetalleNomina detalleNomina = detalleNominaDao.save(pDetalleNomina);
		detalleNomina.setContrato(pDetalleNomina.getContrato());
		return detalleNomina;
	}

	private Boolean validarExistenciaNomina() {
		boolean bandera = false;
		NominaP validarNomina = nominaDao.validarPeriodoNomina(this.empleadoNomina.getContratoId(),
				this.empleadoNomina.getNomina().getFechaFin());
		if (validarNomina != null) {
			bandera = true;
		}
		return bandera;
	}

	private NominaP validarPeriodoNominaEmpleado() {
		Date fechaInicio = this.empleadoNomina.getNomina().getFechaInicio();
		Date fechaFin = this.empleadoNomina.getNomina().getFechaFin();
		NominaP validarPeriodoNomina = this.empleadoNomina.getNomina();
		if (nominaDao.validaNomina(fechaInicio, fechaFin) != null) {
			validarPeriodoNomina = nominaDao.validaNomina(fechaInicio, fechaFin);
		}
		return validarPeriodoNomina;
	}

	private SeguridadSocial generarSeguridadSocial(DetalleNomina pDetalleNomina) {
		Double arl = pDetalleNomina.getContrato().getTarifaArl().getCotizacion()
				* pDetalleNomina.getDevMenosNoSalMenosAuxTrans();
		SeguridadSocial seguridadSocial = SeguridadSocial.builder()
				.detalleNomina(pDetalleNomina).ARL(arl).build();
		return seguridadSocialDao.save(seguridadSocial);
	}

	@Override
	public List<NominaP> listarPeridoNominas() {
		List<NominaP> listaNominas = nominaDao
				.findAll().stream().map(nominas ->{
					nominas.getListaDetalleNomina().forEach(detalleNomina ->{
						Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
						detalleNomina.setContrato(contrato);
					});
					return nominas;
				}).collect(Collectors.toList());
		return listaNominas;
	}

	@Override
	public List<DetalleNomina> listarDetallesNominas() {

		List<DetalleNomina> listaDetallesNominas = detalleNominaDao.findAll().stream().map(detalleNomina -> {
			Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
			detalleNomina.setContrato(contrato);
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
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
					return detalleNomina;
				}).collect(Collectors.toList());
		return listaDetalles;
	}
	
	@Override
	public List<DetalleNomina> listarDetallesNominaPorEstado(String pFechaInicio, String pFechaFin, String pEstado) {
		Date fechaInicio = ParseFecha(pFechaInicio);
		Date fechaFin = ParseFecha(pFechaFin);
		List<DetalleNomina> listaDetalles = 
				detalleNominaDao.listarDetallesNomPorEstado(fechaInicio, fechaFin, pEstado).stream()
				.map(detalleNomina ->{
					Contrato contrato = empresaClient.buscarContratoPorId(detalleNomina.getDetalleNominaPk().getContratoId());
					detalleNomina.setContrato(contrato);
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
		Contrato contrato = empresaClient.buscarContratoPorId(pContratoId);
		detalleNomina.setEstado("PAGADO");
		detalleNomina.setContrato(contrato);
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


}
