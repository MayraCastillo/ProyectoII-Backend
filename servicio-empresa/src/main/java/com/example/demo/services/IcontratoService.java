package com.example.demo.services;

import java.util.List;

import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empleado_banco;
import com.example.demo.entitys.Empleado_tercero;
import com.example.demo.entitys.Empresa;
import com.example.demo.entitys.TarifaArl;
import com.example.demo.entitys.TipoTercero;

public interface IcontratoService {
	public Empleado crearEmpleado(Empleado pEmpleado);
	public Banco crearBanco(Banco pBanco);
	public Contrato crearContrato(Contrato pContrato);
	public Empresa crearEmpresa(Empresa pEmpresa);
	public Empleado_banco crearRelacionEmpBanco(Empleado_banco pEmpleadoBanco);
	public Empleado_tercero crearRelacionEmpTercero(Empleado_tercero pEmpleadoTercero);
	public Empleado editarEmpleado(Empleado pEmpleado);
	public Empleado inhabilitarEmpleado(Long pNumeroDocuemento);
	public Empleado habilitarEmpleado(Long pNumeroDocuemento);
	public Empleado buscarEmpleadoPorNumeroDocumento(Long pNumeroDocumento);
	public Contrato buscarContratoPorId(Long pIdContrato);
	public Contrato finalizarContrato(Long pContratoId);
	public List<Empleado_banco> buscarRelacionEmpleadoBanco(Long pNumeroDocumento);
	public List<Empleado> listarEmpleados();
	//public List<Empleado> listarEmpleadosPorEmpresa(Long pNitEmpresa);
	//public List<Empleado> listarEmpleadosQuery(Long pNit, String pEstado);
	public List<Empleado> listarEmpleadosPorEstado(String pEstado);
	public List<Banco> listarBancos();
	public List<Contrato> listarContratos(Long PNitEmpresa);
	public List<Contrato> listarContratosPorEstado(String pEstado);
	public List<TipoTercero> listarTipoTeceros();
	public List<TarifaArl> listarTarifasArl();
	public List<Empresa> listarEmpresas();
	public List<Empleado_banco> listarRelacionEmpBanco();
	public List<Empleado_tercero> listarRelacionEmpTercero();
	public String consultarEstadoEmpleado(Long pNumeroDocumento, Long pNitEmpresa);

}
