package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Tercero;

@Repository
public interface IterceroDAO extends JpaRepository<Tercero, Long> {
	public Tercero findByNombre(String pNombre);

	@Query("SELECT t FROM Tercero t WHERE t.tipoTercero.tipoTerceroId = ?1")
	public List<Tercero> listarTercerosPorTipo(Long pTipoId);
	
	@Query("SELECT t FROM Tercero t INNER JOIN t.listaEmp_terceros et WHERE et.empleadoTeceroPk.empleado.numeroDocumento = (?1)")
	public List<Tercero> listarTercerosPorEmpleado(Long pNumDocumento);
}
