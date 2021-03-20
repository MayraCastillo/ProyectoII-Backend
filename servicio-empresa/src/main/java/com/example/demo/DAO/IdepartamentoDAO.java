package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Departamento;

@Repository
public interface IdepartamentoDAO extends JpaRepository<Departamento, Long> {
	public Departamento findByNombre(String pNombre);
}
