package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@ResponseStatus(HttpStatus.CREATED)
	public Tercero crearTercero(@RequestBody Tercero pTercero) {
		return terceroSevice.crearTercero(pTercero);
	}
	
	@PostMapping("/crearTipoTercero")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoTercero crearTipoTercero(@RequestBody TipoTercero pTipoTercero) {
		return terceroSevice.crearTipoTercero(pTipoTercero);
	}

	@PostMapping(value = "/crearPais")
	@ResponseStatus(HttpStatus.CREATED)
	public Pais crearPais(@RequestBody Pais pPais) {
		return ubicacionService.crearPais(pPais);
	}

	@PostMapping(value = "/crearDepartamento")
	@ResponseStatus(HttpStatus.CREATED)
	public Departamento crearDepartamento(@RequestBody Departamento pDepartamento) {
		return ubicacionService.crearDepartamento(pDepartamento);
	}

	@PostMapping(value = "/crearMunicipio")
	@ResponseStatus(HttpStatus.CREATED)
	public Municipio crearMunicipio(@RequestBody Municipio pMunicipio) {
		return ubicacionService.crearMunicipio(pMunicipio);
	}

	@PostMapping(value = "/crearEmpleado")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado crearMunicipio(@RequestBody Empleado pEmpleado) {
		return contratoService.crearEmpleado(pEmpleado);
	}

	@PostMapping(value = "/crearBanco")
	@ResponseStatus(HttpStatus.CREATED)
	public Banco crearBanco(@RequestBody Banco pBanco) {
		return contratoService.crearBanco(pBanco);
	}

	@PostMapping(value = "/crearEmpresa")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa crearEmpresa(@RequestBody Empresa pEmpresa) {
		return contratoService.crearEmpresa(pEmpresa);
	}

	@PostMapping(value = "/crearContrato")
	@ResponseStatus(HttpStatus.CREATED)
	public Contrato crearBanco(@RequestBody Contrato pContrato) {
		return contratoService.crearContrato(pContrato);
	}

	@PostMapping(value = "/crearRelacionEmpleadosBancos")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado_banco crearRelacionEmpBanco(@RequestBody Empleado_banco pEmpleadoBanco) {
		return contratoService.crearRelacionEmpBanco(pEmpleadoBanco);
	}
	
	@PostMapping(value = "/crearRelacionEmpleadosTerceros")
	@ResponseStatus(HttpStatus.CREATED)
	public Empleado_tercero crearRelacionEmpTerceros(@RequestBody Empleado_tercero pEmpleadoTercero) {
		return contratoService.crearRelacionEmpTercero(pEmpleadoTercero);
	}

	@GetMapping("/listarTerceros")
	public List<Tercero> listarTerceros() {
		return terceroSevice.listarTerceros();
	}
	
	@GetMapping("/listarTercerosPorTipo/{pTipoId}")
	public List<Tercero> listarTercerosPorTipo(@PathVariable Long pTipoId) {
		return terceroSevice.listarTercerosPorTipo(pTipoId);
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
	public Empleado editarEmpleado(@RequestBody Empleado pEmpleado) {
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
	
	@GetMapping("/listarContratosPorEmpresa/{pNitEmpresa}")
	public List<Contrato> listarContratosPorEmpresa(@PathVariable Long pNitEmpresa) {
		return contratoService.listarContratosPorEmpresa(pNitEmpresa);
	}
	
	@GetMapping("/listarContratosPorEstado/{pEstado}/{pNitEmpresa}")
	public List<Contrato> listarContratosPorEstado(@PathVariable String pEstado,@PathVariable Long pNitEmpresa ) {
		return contratoService.listarContratosPorEstado(pEstado, pNitEmpresa);
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

}
