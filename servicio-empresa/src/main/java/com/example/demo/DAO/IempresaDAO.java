package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitys.Empresa;

public interface IempresaDAO extends JpaRepository<Empresa, Long> {
	public Empresa findByNombreIgnoreCase(String pNombre);

}
