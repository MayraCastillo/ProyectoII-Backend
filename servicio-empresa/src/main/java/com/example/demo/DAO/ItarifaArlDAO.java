package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.TarifaArl;
@Repository
public interface ItarifaArlDAO extends JpaRepository<TarifaArl, Long> {

}
