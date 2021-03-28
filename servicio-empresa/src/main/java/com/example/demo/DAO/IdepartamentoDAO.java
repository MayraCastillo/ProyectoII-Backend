package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Departamento;

@Repository
public interface IdepartamentoDAO extends JpaRepository<Departamento, Long> {
	public Departamento findByNombreIgnoreCase(String pNombre);
	@Query("SELECT d FROM Departamento d WHERE d.pais.paisId = (?1)")
	public List<Departamento>listarDepartamentosPorPais(Long pPaid);
}
