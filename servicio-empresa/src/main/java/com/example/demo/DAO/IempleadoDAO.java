package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entitys.Empleado;

@Repository
public interface IempleadoDAO extends JpaRepository<Empleado, Long> {
	public List<Empleado> findByNombres(String pNombre);
	public List<Empleado> findByEstadoIgnoreCase(String pNombre);
	public Empleado findByNumeroDocumento(Long pNumeroDocumento);
	
	@Query("SELECT e FROM Empleado e INNER JOIN e.listaContratos c WHERE c.contratoPk.empresa.nit = (?1) AND c.estado = (?2)")
	public List<Empleado> listarEmpleadosPorEstado(Long pNit, String pEstado);
	@Query("SELECT e FROM Empleado e INNER JOIN e.listaContratos c WHERE c.contratoPk.empresa.nit = (?1)")
	public List<Empleado> listarEmpleadosPorEmpresa(Long pNit);
}
