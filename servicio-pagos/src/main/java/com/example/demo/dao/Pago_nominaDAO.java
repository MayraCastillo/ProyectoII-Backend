package com.example.demo.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Pago_nomina;

public interface Pago_nominaDAO extends JpaRepository<Pago_nomina, Long> {
	@Query("SELECT p FROM Pago_nomina p WHERE p.fechaInicio = ?1 AND p.fechaFin = ?2")
	public Pago_nomina buscarPorPerido(Date pFechaInicio,Date pFechaFin);

}
