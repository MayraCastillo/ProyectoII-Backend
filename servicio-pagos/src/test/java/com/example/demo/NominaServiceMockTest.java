package com.example.demo;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.dao.DetalleNominaDAO;
import com.example.demo.dao.FactoresNoSalarialesDAO;
import com.example.demo.dao.FactoresSalarialesDAO;
import com.example.demo.dao.NominaPDAO;
import com.example.demo.dao.RegistroHorasPDAO;
import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.entity.RegistroHorasP;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Contrato;
import com.example.demo.model.EmpleadoNominaP;
import com.example.demo.service.ServicioParametrosLegales;

@SpringBootTest
public class NominaServiceMockTest {

	@Mock
	private DetalleNominaDAO detalleNominaDao;

	@Mock
	private NominaPDAO nominaDao;

	@Mock
	private RegistroHorasPDAO registroHorasDao;

	@Mock
	private FactoresSalarialesDAO factoresSalarialesDao;

	@Mock
	private FactoresNoSalarialesDAO factoresNoSalarialesDao;

	@Autowired
	ServicioParametrosLegales parametrosLegales;

	@Mock
	EmpresaClient empresaClient;

	public Contrato crearContrato() {
		Contrato contrato = Contrato.builder().contratoId(1L).fechaFinContrato(new Date()).salarioBase(1000000.0)
				.build();
		return contrato;
	}

	public RegistroHorasP crearRegistroHoras() {
		RegistroHorasP registroHoras = RegistroHorasP.builder()
				.horasTrabajadas(240)
				.extrasDiurnoOrdinaro(3)
				.extrasNoturnoOrdinario(0)
				.extrasDominicalFestivoDiurno(0)
				.extrasDominicalFestivoNoturno(0)
				.recargoNoturno(1)
				.recargoDiurnoDominicalFestivo(0)
				.recargoNoturnoDominicalFestivo(0).build();
		return registroHoras;
	}

	public NominaP crearNomina() {
		NominaP pagoNomina = NominaP.builder()
				.fechaInicio(new Date())
				.fechaFin(new Date())
				.detalle("Detalle Nomina")
				.estado("Generada").build();
		return pagoNomina;
	}

	public FactoresSalariales crearFactoresSalariales() {
		FactoresSalariales factorSalarial = FactoresSalariales.builder()
				.comisiones(80000.0)
				.bonificaciones(10000.0)
				.auxilioExtra(10000.0)
				.viaticos(10000.0)
				.otros(10000.0).build();
		return factorSalarial;
	}

	public FactoresNoSalariales crearFactoresNoSalariales() {
		FactoresNoSalariales factorSalarial = FactoresNoSalariales.builder()
				.comisiones(60000.0)
				.bonificaciones(20000.0)
				.auxilioExtra(20000.0)
				.viaticos(20000.0)
				.otros(20000.0).build();
		return factorSalarial;
	}

	public EmpleadoNominaP crearEmpleadoNomina() {
		Contrato contrato = crearContrato();
		EmpleadoNominaP empleadoNomina = EmpleadoNominaP.builder().salarioBase(contrato.getSalarioBase())
				.contratoId(contrato.getContratoId()).registroHoras(crearRegistroHoras())
				.factoresSalariales(crearFactoresSalariales()).factoresNoSalariales(crearFactoresNoSalariales())
				.nomina(crearNomina()).build();
		return empleadoNomina;
	}

	public Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}

	public Double calcularBasicoDevengadoPrueba() {
		EmpleadoNominaP empleadoNomina = crearEmpleadoNomina();
		Integer horasTrabajadas = empleadoNomina.getRegistroHoras().getHorasTrabajadas();
		Double salarioBase = empleadoNomina.getSalarioBase();
		Double basicoDevengado = redondeo((salarioBase / 240) * horasTrabajadas);
		return basicoDevengado;
	}

	public Double RetornarSalarioMinimoPrueba() {
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("SALARIO MINIMO");
		Double salarioMinimo = parametroLegal.getValor();
		return salarioMinimo;
	}

	public Double calcularAuxilioTransportePrueba() {
		Double auxilioTransporte = 0.0;
		Integer horasTrabajadas = crearEmpleadoNomina().getRegistroHoras().getHorasTrabajadas();
		ParametroLegal parametroLegal = parametrosLegales.buscarParametroPorNombre("AUXILIO DE TRANSPORTE");
		Double valorAuxilioTransporte = parametroLegal.getValor();
		if (calcularBasicoDevengadoPrueba() <= 2 * RetornarSalarioMinimoPrueba()) {
			auxilioTransporte = redondeo((valorAuxilioTransporte / 240) * horasTrabajadas);
		}
		return auxilioTransporte;
	}

	public Double calcularHorasExtrasPrueba() {
		EmpleadoNominaP EmpleadoNomina = crearEmpleadoNomina();
		RegistroHorasP registroHoras = EmpleadoNomina.getRegistroHoras();
		ParametroLegal PorcentajeExtDiurnaOrdinaria = parametrosLegales.buscarParametroPorId(5L);
		ParametroLegal PorcentajeExtNoturnaOrdinaria = parametrosLegales.buscarParametroPorId(6L);
		ParametroLegal PorcentajeExtDiurDomOfestiva = parametrosLegales.buscarParametroPorId(7L);
		ParametroLegal PorcentajeExtNoturDomOfestiva = parametrosLegales.buscarParametroPorId(8L);

		Double diurnaOrdinaria = (calcularBasicoDevengadoPrueba() / 240) * registroHoras.getExtrasDiurnoOrdinaro()
				* PorcentajeExtDiurnaOrdinaria.getValor();
		Double noturnaOrdinaria = (calcularBasicoDevengadoPrueba() / 240) * registroHoras.getExtrasNoturnoOrdinario()
				* PorcentajeExtNoturnaOrdinaria.getValor();
		Double diurnaDomFestiva = (calcularBasicoDevengadoPrueba() / 240)
				* registroHoras.getExtrasDominicalFestivoDiurno() * PorcentajeExtDiurDomOfestiva.getValor();
		Double noturnoDomFestiva = (calcularBasicoDevengadoPrueba() / 240)
				* registroHoras.getExtrasDominicalFestivoNoturno() * PorcentajeExtNoturDomOfestiva.getValor();
		Double horasExtras = redondeo(diurnaOrdinaria + noturnaOrdinaria + diurnaDomFestiva + noturnoDomFestiva);

		return horasExtras;
	}

	public Double calcularRecargosPrueba() {
		Double recargos = 0.0;
		EmpleadoNominaP empleadoNomina = crearEmpleadoNomina();
		RegistroHorasP registroHoras = empleadoNomina.getRegistroHoras();
		ParametroLegal pRecargoNoturno = parametrosLegales.buscarParametroPorId(9L);
		ParametroLegal pRecargoDiurDomOfestivo = parametrosLegales.buscarParametroPorId(10L);
		ParametroLegal pRecagoNoturDomOfestivo = parametrosLegales.buscarParametroPorId(11L);

		Double recargoNoturno = (calcularBasicoDevengadoPrueba() / 240) * registroHoras.getRecargoNoturno()
				* pRecargoNoturno.getValor();
		Double recargoDiurDomOfestivo = (calcularBasicoDevengadoPrueba() / 240)
				* registroHoras.getRecargoDiurnoDominicalFestivo() * pRecargoDiurDomOfestivo.getValor();
		Double recargoNoturDomOfestivo = (calcularBasicoDevengadoPrueba() / 240)
				* registroHoras.getRecargoNoturnoDominicalFestivo() * pRecagoNoturDomOfestivo.getValor();
		recargos = redondeo(recargoNoturno + recargoDiurDomOfestivo + recargoNoturDomOfestivo);

		return recargos;
	}

	public Double obtenerComisionesPrueba() {
		EmpleadoNominaP empleadoNomina = crearEmpleadoNomina();
		return empleadoNomina.getFactoresSalariales().getComisiones();
	}

	public Double ingresoSalarialPrueba() {
		Double ingresoSalarial = 0.0;
		EmpleadoNominaP empleadoNomina = crearEmpleadoNomina();
		FactoresSalariales factoresSalariales = empleadoNomina.getFactoresSalariales();
		ingresoSalarial = redondeo(factoresSalariales.getAuxilioExtra() + factoresSalariales.getBonificaciones()
				+ factoresSalariales.getComisiones() + factoresSalariales.getOtros()
				+ factoresSalariales.getViaticos());
		return ingresoSalarial;
	}

	public Double ingresoNoSalarialPrueba() {
		Double ingresoNoSalarial = 0.0;
		EmpleadoNominaP empleadoNomina = crearEmpleadoNomina();
		FactoresNoSalariales factoresNoSalariales = empleadoNomina.getFactoresNoSalariales();
		ingresoNoSalarial = redondeo(factoresNoSalariales.getAuxilioExtra() + factoresNoSalariales.getBonificaciones()
				+ factoresNoSalariales.getComisiones() + factoresNoSalariales.getOtros()
				+ factoresNoSalariales.getViaticos());
		return ingresoNoSalarial;
	}

	public Double calcularTotalDevengadoPrueba() {
		return redondeo(calcularBasicoDevengadoPrueba() + calcularAuxilioTransportePrueba()
				+ calcularHorasExtrasPrueba() + calcularRecargosPrueba() + obtenerComisionesPrueba()
				+ ingresoSalarialPrueba() + ingresoNoSalarialPrueba());
	}

	public Double totalDevengadoMenosNoSalarialPrueba() {
		return redondeo(calcularTotalDevengadoPrueba() - ingresoNoSalarialPrueba());
	}
	
	public Double totalDevengadoMenosNoSalarialMenosAuxTransportePrueba() {
		return redondeo(calcularTotalDevengadoPrueba() - ingresoNoSalarialPrueba() - calcularAuxilioTransportePrueba());
	}

	public Double calcularSaludEmpleadoPrueba() {
		ParametroLegal porcentajeSaludEmpleado = parametrosLegales.buscarParametroPorId(3L);
		return redondeo(totalDevengadoMenosNoSalarialMenosAuxTransportePrueba() * porcentajeSaludEmpleado.getValor());
	}
	
	public Double calcularPensionEmpleadoPrueba() {
		ParametroLegal porcentajePensionEmpleado = parametrosLegales.buscarParametroPorId(4L);
		return redondeo(totalDevengadoMenosNoSalarialMenosAuxTransportePrueba() * porcentajePensionEmpleado.getValor());
	}
	
	public Double calcularFondoSolidarioPensionalPrueba() {
		Double fondoSolPensional = 0.0;
		Double salarioMinimo = RetornarSalarioMinimoPrueba();
		if (totalDevengadoMenosNoSalarialMenosAuxTransportePrueba() >= 4 * salarioMinimo) {
			fondoSolPensional = totalDevengadoMenosNoSalarialMenosAuxTransportePrueba() * 0.01;
		}
		return redondeo(fondoSolPensional);
	}
	
	public Double calcularTotalDeduccionesPrueba() {
		return redondeo(calcularSaludEmpleadoPrueba() + calcularPensionEmpleadoPrueba() + calcularFondoSolidarioPensionalPrueba());
	}
	
	public Double calcularNetoPagadoPrueba() {
		return redondeo(calcularTotalDevengadoPrueba() - calcularTotalDeduccionesPrueba());
	}

	@Test
	public void calcularBasicoDevengado() {
		assertThat(calcularBasicoDevengadoPrueba()).isEqualTo(1000000.0);
	}

	@Test
	public void RetornarSalarioMinimo() {
		assertThat(RetornarSalarioMinimoPrueba()).isEqualTo(908526.0);
	}

	@Test
	public void calcularAuxilioTransporte() {
		assertThat(calcularAuxilioTransportePrueba()).isEqualTo(106454.0);
	}

	@Test
	public void calcularHorasExtras() {
		assertThat(calcularHorasExtrasPrueba()).isEqualTo(15625.0);
	}

	@Test
	public void calcularRecargos() {
		assertThat(calcularRecargosPrueba()).isEqualTo(1458.0);
	}

	@Test
	public void obtenerComisiones() {
		assertThat(obtenerComisionesPrueba()).isEqualTo(80000.0);
	}

	@Test
	public void ingresoSalarial() {
		assertThat(ingresoSalarialPrueba()).isEqualTo(120000.0);
	}

	@Test
	public void ingresoNoSalarial() {
		assertThat(ingresoNoSalarialPrueba()).isEqualTo(140000.0);
	}

	@Test
	public void calcularTotalDevengado() {
		assertThat(calcularTotalDevengadoPrueba()).isEqualTo(1463537.0);
	}

	@Test
	public void totalDevengadoMenosNoSalarial() {
		assertThat(totalDevengadoMenosNoSalarialPrueba()).isEqualTo(1323537.0);
	}

	@Test
	public void totalDevengadoMenosNoSalarialMenosAuxTransporte() {
		assertThat(totalDevengadoMenosNoSalarialMenosAuxTransportePrueba())
				.isEqualTo(1217083.0);
	}

	@Test
	public void calcularSaludEmpleado() {
		assertThat(calcularSaludEmpleadoPrueba()).isEqualTo(48683.0);
	}

	@Test
	public void calcularPensionEmpleado() {
		assertThat(calcularPensionEmpleadoPrueba()).isEqualTo(48683.0);
	}

	@Test
	public void calcularFondoSolidarioPensional() {
		assertThat(calcularFondoSolidarioPensionalPrueba()).isEqualTo(0.0);
	}

	@Test
	public void calcularTotalDeducciones() {
		assertThat(calcularTotalDeduccionesPrueba()).isEqualTo(97366.0);
	}

	@Test
	public void calcularNetoPagado() {
		assertThat(calcularNetoPagadoPrueba()).isEqualTo(1366171.0);
	}

	/*
	 * @BeforeEach public void setup() { MockitoAnnotations.initMocks(this);
	 * servicioNominaImpl = new
	 * IservicioNominaImpl(registroHorasDao,factoresDao,pagoNominaDao,nominaDao,
	 * parametrosLegales,empresaClient); Nomina nomina =
	 * Nomina.builder().contratoId(crearContrato().getContratoId()).contrato(
	 * crearContrato())
	 * .pagoNomina(crearPagoNomina()).BasicoDevengado(calcularBasicoDevengado())
	 * .auxilioTransporte(calcularAuxilioTransporte()).horasExtras(
	 * calcularHorasExtras())
	 * .recargos(calcularRecargos()).comisiones(obtenerComisiones()).
	 * otrosIngresoSalarial(ingresoSalarial())
	 * .otrosIngresoNoSalarial(ingresoNoSalarial()).totalDevengado(
	 * calcularTotalDevengado())
	 * .devengadoMenosNoSalariales(totalDevengadoMenosNoSalarial())
	 * .devMenosNoSalMenosAuxTrans(totalDevengadoMenosNoSalarialMenosAuxTransporte()
	 * ) .saludEmpleado(calcularSaludEmpleado()).pensionEmpleado(
	 * calcularPensionEmpleado())
	 * .fondoSolidaridadPensional(calcularFondoSolidarioPensional())
	 * .totalDeducciones(calcularTotalDeducciones()).netoPagado(calcularNetoPagado()
	 * ).build(); Mockito.when(nominaDao.save(nomina)).thenReturn(nomina);
	 * Mockito.when(nominaDao.validarNomina(crearContrato().getContratoId(),
	 * crearContrato().getFechaFinContrato())) .thenReturn(nomina);
	 * Mockito.when(pagoNominaDao.buscarPorPeriodo(new Date(), new
	 * Date())).thenReturn(crearPagoNomina());
	 * Mockito.when(registroHorasDao.save(crearRegistroHoras())).thenReturn(
	 * crearRegistroHoras());
	 * Mockito.when(factoresDao.save(crearFactoresSalariales())).thenReturn(
	 * crearFactoresSalariales());
	 * Mockito.when(empresaClient.buscarContratoPorId(crearContrato().getContratoId(
	 * ))).thenReturn(crearContrato()); }
	 */

}
