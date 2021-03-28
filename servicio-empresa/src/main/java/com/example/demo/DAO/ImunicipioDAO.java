package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entitys.Municipio;

@Repository
public interface ImunicipioDAO extends JpaRepository<Municipio, Long> {
	public Municipio findByNombreIgnoreCase(String pNombre);
	@Query("SELECT m FROM Municipio m WHERE m.departamento.departamentoId = (?1)")
	public List<Municipio>listarMunicipiosPorDepartamento(Long pDepartamentoId);
	@Query("SELECT m FROM Municipio m WHERE m.codigo = (?1) AND m.departamento.departamentoId = (?2)")
	public Municipio validarExistenciaMunicipio(Integer pCodigoId, Long pDepartamentoId);
}
