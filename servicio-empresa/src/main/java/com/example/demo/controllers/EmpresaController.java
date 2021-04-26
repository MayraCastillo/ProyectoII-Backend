package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Departamento;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empleado_banco;
import com.example.demo.entitys.Empleado_tercero;
import com.example.demo.entitys.Empresa;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.Pais;
import com.example.demo.entitys.TarifaArl;
import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;
import com.example.demo.services.IcontratoService;
import com.example.demo.services.IterceroService;
import com.example.demo.services.IubicacionService;

@RestController
public class EmpresaController {

	@Autowired
	private IterceroService terceroSevice;

	@Autowired
	private IubicacionService ubicacionService;

	@Autowired
	private IcontratoService contratoService;

	@PostMapping("/crearTercero")
	public ResponseEntity<Tercero> crearTercero(@RequestBody @Valid Tercero pTercero) {
		Tercero tercero = terceroSevice.crearTercero(pTercero);
		if(tercero == null) 
		{
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(tercero);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(tercero);
	}
	
	@PostMapping("/crearTipoTercero")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoTercero crearTipoTercero(@RequestBody @Valid TipoTercero pTipoTercero) {
		return terceroSevice.crearTipoTercero(pTipoTercero);
	}

	@PostMapping(value = "/crearPais")
	@ResponseStatus(HttpStatus.CREATED)
	public Pais crearPais(@RequestBody @Valid Pais pPais) {
		return ubicacionService.crearPais(pPais);
	}

	@PostMapping(value = "/crearDepartamento")
	@ResponseStatus(HttpStatus.CREATED)
	public Departamento crearDepartamento(@RequestBody @Valid Departamento pDepartamento) {
		return ubicacionService.crearDepartamento(pDepartamento);
	}

	@PostMapping(value = "/crearMunicipio")
	@ResponseStatus(HttpStatus.CREATED)
	public Municipio crearMunicipio(@RequestBody @Valid Municipio pMunicipio) {
		return ubicacionService.crearMunicipio(pMunicipio);
	}

	@PostMapping(value = "/crearEmpleado")
	public ResponseEntity<Empleado> crearEmpleado(@RequestBody @Valid Empleado pEmpleado) {
        Empleado empleado =  contratoService.crearEmpleado(pEmpleado);        
        if (empleado == null){
        	System.out.println("Error");
        	return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(empleado);
        }
		return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
	}

	@PostMapping(value = "/crearBanco")
	@ResponseStatus(HttpStatus.CREATED)
	public Banco crearBanco(@RequestBody @Valid Banco pBanco) {
		return contratoService.crearBanco(pBanco);
	}

	@PostMapping(value = "/crearEmpresa")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa crearEmpresa(@RequestBody @Valid Empresa pEmpresa) {
		return contratoService.crearEmpresa(pEmpresa);
	}

	@PostMapping(value = "/crearContrato")
	public ResponseEntity<Contrato> crearContrato(@RequestBody @Valid Contrato pContrato) {
        Contrato contrato =  contratoService.crearContrato(pContrato);        
        
        if (contrato == null){
        	System.out.println("Error");
        	return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(contrato);
        }   
		return ResponseEntity.status(HttpStatus.CREATED).body(contrato);
	}

	@PostMapping(value = "/crearRelacionEmpleadosBancos")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado_banco crearRelacionEmpBanco(@RequestBody @Valid Empleado_banco pEmpleadoBanco) {
		return contratoService.crearRelacionEmpBanco(pEmpleadoBanco);
	}
	
	@PostMapping(value = "/crearRelacionEmpleadosTerceros")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado_tercero crearRelacionEmpTerceros(@RequestBody @Valid Empleado_tercero pEmpleadoTercero) {
		return contratoService.crearRelacionEmpTercero(pEmpleadoTercero);
	}
	
	@GetMapping(value = "/buscarContratoPorId/{pIdContrato}")
	public Contrato buscarContratoPorId(@PathVariable Long pIdContrato) {
		return contratoService.buscarContratoPorId(pIdContrato);
	}
	
	@GetMapping(value = "/buscarEmpleadoPorNumeroDocumento/{pNumeroDocumento}")
	public ResponseEntity<Empleado> buscarEmpleadoPorNumeroDocumento(@PathVariable Long pNumeroDocumento){
		Empleado empleado = contratoService.buscarEmpleadoPorNumeroDocumento(pNumeroDocumento);
		if(empleado == null) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(empleado);
		}
		return ResponseEntity.status(HttpStatus.OK).body(empleado);
	}
	
	@GetMapping(value = "/buscarRelacionEmpleadoBanco/{pNumeroDocumento}")
	public ResponseEntity<List<Empleado_banco>> buscarRelacionEmpleadoBanco(@PathVariable Long pNumeroDocumento){
		List<Empleado_banco> listEmpleadoBanco = contratoService.buscarRelacionEmpleadoBanco(pNumeroDocumento);
		if(listEmpleadoBanco == null) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listEmpleadoBanco);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listEmpleadoBanco);
	}
	
	@GetMapping(value = "/buscarTerceroPorNit/{pNit}")
	public ResponseEntity<Tercero> buscarTerceroPorNIt(@PathVariable Long pNit)
	{
		Tercero tercero = terceroSevice.buscarTerceroPorNit(pNit);
		if(tercero == null) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tercero);
		}
		return ResponseEntity.status(HttpStatus.OK).body(tercero);
	}
	
	@PutMapping(value = "/actualizarTercero/{pNit}")
	public ResponseEntity<Tercero> actualizarTercero(@PathVariable Long pNit,@RequestBody @Valid Tercero pTercero)
	{
		pTercero.setNit(pNit);
		Tercero tercero = terceroSevice.actualizarTercero(pTercero);
		if(tercero == null) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tercero);
		}
		return ResponseEntity.status(HttpStatus.OK).body(tercero);
	}

	@GetMapping("/listarTerceros")
	public List<Tercero> listarTerceros() {
		return terceroSevice.listarTerceros();
	}
	
	@GetMapping("/listarTercerosPorTipo/{pTipoId}")
	public ResponseEntity<List<Tercero>> listarTercerosPorTipo(@PathVariable Long pTipoId) {
		List<Tercero> terceros = terceroSevice.listarTercerosPorTipo(pTipoId);
		if(terceros.isEmpty()) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(terceros);
		}
		return ResponseEntity.status(HttpStatus.OK).body(terceros);
	}
	
	@GetMapping("/listarTercerosPorEmpleado/{pNumDocumento}")
	public List<Tercero> listarTercerosPorEmpleado(@PathVariable Long pNumDocumento) {
		return terceroSevice.listarTercerosPorEmpleado(pNumDocumento);
	}

	@GetMapping(value = "/listarPaises")
	public List<Pais> listar() {
		return ubicacionService.listarPaises();
	}

	@GetMapping(value = "/listarDepartamentos")
	public List<Departamento> listarDepartamentos() {
		return ubicacionService.listarDepartamentos();
	}

	@GetMapping(value = "/listarMunicipios")
	public List<Municipio> listarMunicipios() {
		return ubicacionService.listarMunicipios();
	}
	
	@GetMapping("/listarEmpleados")
	public List<Empleado> listarEmpleados() {
		return contratoService.listarEmpleados();
	}

	@GetMapping("/listarEmpleadosPorEmpresa/{pNitEmpresa}")
	public List<Empleado> listarEmpleadosPorEmpresa(@PathVariable Long pNitEmpresa ) {
		return contratoService.listarEmpleadosPorEmpresa(pNitEmpresa);
	}

	@GetMapping("/listarEmpleadosPorEstadoQuery/{pNit}/{pEstado}")
	public List<Empleado> listarEmpleadosPorEstado(@PathVariable Long pNit, @PathVariable String pEstado) {
		return contratoService.listarEmpleadosQuery(pNit, pEstado);
	}

	@GetMapping("/listarEmpleadosPorEstado/{pEstado}")
	public List<Empleado> listarEmpleadosPorEstado(@PathVariable String pEstado) {
		return contratoService.listarEmpleadosPorEstado(pEstado);
	}

	@GetMapping("/listarBancos")
	public List<Banco> listarBancos() {
		return contratoService.listarBancos();
	}

	@PostMapping(value = "/editarEmpleado")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado editarEmpleado(@RequestBody @Valid Empleado pEmpleado) {
		return contratoService.editarEmpleado(pEmpleado);
	}

	@GetMapping(value = "/buscarDepartamentos/{pIdDepartamento}")
	public Departamento buscarDepartemanto(@PathVariable Long pIdDepartamento) {
		return ubicacionService.buscarDepartamento(pIdDepartamento);
	}

	@GetMapping("/listarContratos")
	public List<Contrato> listarContratos() {
		return contratoService.listarContratos();
	}
	
	@GetMapping("/listarContratosPorEstado/{pNitEmpresa}")
	public List<Contrato> listarContratosPorEstado(@PathVariable Long pNitEmpresa ) {
		return contratoService.listarContratosPorEstados(pNitEmpresa);
	}

	@GetMapping("/listarTipoTerceros")
	public List<TipoTercero> listarTipoTerceros() {
		return contratoService.listarTipoTeceros();
	}

	@GetMapping("/listarTarifasArl")
	public List<TarifaArl> listarTarifasArl() {
		return contratoService.listarTarifasArl();
	}

	@GetMapping("/listarEmpresas")
	public List<Empresa> listarEmpresas() {
		return contratoService.listarEmpresas();
	}

	@GetMapping("/listarDepartamentosPorPais/{pIdPais}")
	public List<Departamento> listarDepPorPais(@PathVariable Long pIdPais) {
		return ubicacionService.listarDepartamentosPorPais(pIdPais);
	}

	@GetMapping("/listarMunicipiosPorDepartamento/{pIdDepartamento}")
	public List<Municipio> listarMunPorDepartamento(@PathVariable Long pIdDepartamento) {
		return ubicacionService.listarMunicipiosPorDepartamento(pIdDepartamento);
	}

	@GetMapping("/buscarMunicipioPorId/{pIdMunicipio}")
	public Municipio buscarMunPorId(@PathVariable Long pIdMunicipio) {
		return ubicacionService.buscarMunicipioPorId(pIdMunicipio);
	}

	@GetMapping("/listarRelacionesEmpleados_bancos")
	public List<Empleado_banco> listarRelacionEmpBancos() {
		return contratoService.listarRelacionEmpBanco();

	}
	
	@GetMapping("/listarRelacionesEmpleados_terceros")
	public List<Empleado_tercero> listarRelacionEmpleadosTerceros() {
		return contratoService.listarRelacionEmpTercero();

	}
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/consultarEstadoEmpleado/{pNumeroDocumento}/{pNit}")
	public String consultarEstadoEmpleado(@PathVariable Long pNumeroDocumento, @PathVariable Long pNit) {
		return this.contratoService.consultarEstadoEmpleado(pNumeroDocumento, pNit);
	}

}
