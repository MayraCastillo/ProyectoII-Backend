package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Empleado_banco;
import com.example.demo.models.Empleado_banco_pk;

@Repository
public interface Iempleado_banco extends JpaRepository<Empleado_banco, Empleado_banco_pk> {

}
