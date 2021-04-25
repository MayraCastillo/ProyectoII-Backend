package com.example.demo.services;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DAO.IbancoDAO;
import com.example.demo.DAO.IcontratoDAO;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.DAO.IempleadoTerceroDAO;
import com.example.demo.DAO.Iempleado_banco;
import com.example.demo.DAO.IempresaDAO;
import com.example.demo.DAO.ImunicipioDAO;
import com.example.demo.DAO.ItarifaArlDAO;
import com.example.demo.DAO.IterceroDAO;
import com.example.demo.DAO.ItipoTerceroDAO;
import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empleado_banco;
import com.example.demo.entitys.Empleado_tercero;
import com.example.demo.entitys.Empresa;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.TarifaArl;
import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IcontratoServiceImpl implements IcontratoService {

	
	private final IempleadoDAO empleadoDAO;
	private final IcontratoDAO contratoDAO;

	@Autowired
	private IbancoDAO bancoDAO;

	@Autowired
	private ImunicipioDAO municipioDAO;

	@Autowired
	private ItipoTerceroDAO tipoTerceroDAO;

	@Autowired
	private ItarifaArlDAO tarifaArlDAO;

	@Autowired
	private IempresaDAO empresaDAO;

	@Autowired
	private Iempleado_banco empleado_bancoDAO;

	@Autowired
	private IempleadoTerceroDAO empleado_terceroDAO;

	@Autowired
	private IterceroDAO terceroDAO;

	@Override
	public Empleado crearEmpleado(Empleado pEmpleado) {
		Empleado empleado = empleadoDAO.findByNumeroDocumento(pEmpleado.getNumeroDocumento());
		if (empleado != null) {
			return null;
		}
		Municipio municipio = municipioDAO.findById(pEmpleado.getMunicipio().getMunicipio_id()).orElse(null);
		pEmpleado.setMunicipio(municipio);
		pEmpleado.setEstado("ACTIVO");
		return empleadoDAO.save(pEmpleado);
	}

	@Override
	public Banco crearBanco(Banco pBanco) {
		Banco banco = bancoDAO.findByNombreIgnoreCase(pBanco.getNombre());
		if (banco != null) {
			return banco;
		}

		return bancoDAO.save(pBanco);
	}

	@Override
	public Empleado_tercero crearRelacionEmpTercero(Empleado_tercero pEmpleadoTercero) {
		Tercero tercero = terceroDAO.findById(pEmpleadoTercero.getEmpleadoTeceroPk().getTercero().getNit())
				.orElse(null);
		Empleado_tercero empleadoTercero = empleado_terceroDAO.validacionEmpleadoTercero(
				tercero.getTipoTercero().getAbreviacion(),
				pEmpleadoTercero.getEmpleadoTeceroPk().getEmpleado().getNumeroDocumento());
		if (empleadoTercero != null) {
			return empleadoTercero;
		}
		pEmpleadoTercero.setEstado("ACTIVO");
		return empleado_terceroDAO.save(pEmpleadoTercero);
	}

	@Override
	public Contrato crearContrato(Contrato pContrato) {
		Long numeroDocumento = pContrato.getContratoPk().getEmpleado().getNumeroDocumento();
		Long nit = pContrato.getContratoPk().getEmpresa().getNit();
		Contrato contrato = contratoDAO.VerificarContrato(numeroDocumento, nit);
		if (contrato != null) {
			return null;
		}
		pContrato.setEstado("ACTIVO");
		return contratoDAO.save(pContrato);
	}

	@Override
	public Empresa crearEmpresa(Empresa pEmpresa) {
		Empresa empresa = empresaDAO.findById(pEmpresa.getNit()).orElse(null);
		if (empresa != null) {
			return empresa;
		}
		return empresaDAO.save(pEmpresa);
	}

	@Override
	public Empleado_banco crearRelacionEmpBanco(Empleado_banco pEmpleadoBanco) {
		pEmpleadoBanco.setEstado("ACTIVO");
		return empleado_bancoDAO.save(pEmpleadoBanco);
	}

	@Override
	public Empleado editarEmpleado(Empleado pEmpleado) {
		Empleado empleado = empleadoDAO.findByNumeroDocumento(pEmpleado.getNumeroDocumento());
		if (empleado == null) {
			return null;
		}
		empleado.setApellidos(empleado.getApellidos());
		empleado.setCorreo(empleado.getCorreo());
		empleado.setDireccion(empleado.getDireccion());
		empleado.setEstado(empleado.getEstado());
		empleado.setMunicipio(empleado.getMunicipio());
		empleado.setNombres(empleado.getNombres());
		empleado.setTelefono(empleado.getTelefono());
		empleado.setTipoDocumento(empleado.getTipoDocumento());
		return empleadoDAO.save(empleado);
	}

	@Override
	public Contrato buscarContratoPorId(Long pIdContrato) {
		return contratoDAO.findById(pIdContrato).orElse(null);
	}
	
	@Override
	public Empleado buscarEmpleadoPorNumeroDocumento(Long pNumeroDocumento) {
		
		return empleadoDAO.findByNumeroDocumento(pNumeroDocumento);
	}


	@Override
	public List<Empleado> listarEmpleadosPorEmpresa(Long pNitEmpresa) {
		return empleadoDAO.listarEmpleadosPorEmpresa(pNitEmpresa);
	}

	@Override
	public List<Empleado> listarEmpleadosPorEstado(String pEstado) {
		return empleadoDAO.findByEstadoIgnoreCase(pEstado);
	}

	@Override
	public List<Banco> listarBancos() {
		return bancoDAO.findAll();
	}

	@Override
	public List<Contrato> listarContratos() {
		return contratoDAO.findAll();
	}

	private Date validacionFecha() {
		Date myDate = new Date();
		return myDate;
	}
	
	private void actualizarEstadoContratos() 
	{
		contratoDAO.findAll().forEach (contrato ->{
			if (contrato.getFechaFinContrato().before(validacionFecha())) {
				contrato.setEstado("INACTIVO");
				contratoDAO.save(contrato);
			}
        });
		
	}

	@Override
	public List<Contrato> listarContratosPorEstados(Long pNitEmpresa) {
		actualizarEstadoContratos();
		return contratoDAO.listarContratosPorEstado(pNitEmpresa);
	}

	@Override
	public List<TipoTercero> listarTipoTeceros() {

		return tipoTerceroDAO.findAll();
	}

	@Override
	public List<TarifaArl> listarTarifasArl() {
		return tarifaArlDAO.findAll();
	}

	@Override
	public List<Empresa> listarEmpresas() {
		return empresaDAO.findAll();
	}

	@Override
	public List<Empleado> listarEmpleadosQuery(Long pNit, String pEstado) {
		return empleadoDAO.listarEmpleadosPorEstado(pNit, pEstado);
	}

	@Override
	public List<Empleado_banco> listarRelacionEmpBanco() {
		return empleado_bancoDAO.findAll();
	}

	@Override
	public List<Empleado_tercero> listarRelacionEmpTercero() {
		return empleado_terceroDAO.findAll();
	}

	@Override
	public List<Empleado> listarEmpleados() {
		return empleadoDAO.findAll();
	}

	
	
	
	
	
	
	
	
	
	@Override
	public String consultarEstadoEmpleado(Long pNumeroDocumento, Long pNitEmpresa) {
		String estado = null;
		if(pNumeroDocumento != null && pNitEmpresa != null) {
			Contrato contrato = contratoDAO.VerificarContrato(pNumeroDocumento, pNitEmpresa);
			if (contrato != null) {
				estado = contrato.getEstado();
				
				if (contrato.getFechaFinContrato().before(validacionFecha())) {
					contrato.setEstado("INACTIVO");
				}
			}
		}
		return estado;
	}


}
