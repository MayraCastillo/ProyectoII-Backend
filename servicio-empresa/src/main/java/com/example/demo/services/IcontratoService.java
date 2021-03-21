package com.example.demo.services;

import java.util.List;

import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empresa;
import com.example.demo.entitys.TarifaArl;
import com.example.demo.entitys.TipoTercero;

public interface IcontratoService {
	public Empleado crearEmpleado(Empleado pEmpleado);
	public Banco crearBanco(Banco pBanco);
	public Contrato crearContrato(Contrato pContrato);
	public Empresa crearEmpresa(Empresa pEmpresa);
	public Empleado editarEmpleado(Empleado pEmpleado);
	public List<Empleado> listarEmpleados();
	public List<Empleado> listarEmpleadosQuery(Long pNit, String pEstado);
	public List<Empleado> listarEmpleadosPorEstado(String pEstado);
	public List<Banco> listarBancos();
	public List<Contrato> listarContratos();
	public List<TipoTercero> listarTipoTeceros();
	public List<TarifaArl> listarTarifasArl();
	public List<Empresa> listarEmpresas();

}
