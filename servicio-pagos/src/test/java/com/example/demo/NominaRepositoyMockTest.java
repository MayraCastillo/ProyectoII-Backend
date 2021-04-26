package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.dao.NominaDAO;
import com.example.demo.dao.ParametrosLegalesDAO;

@DataJpaTest
public class NominaRepositoyMockTest {
	@Autowired
	private NominaDAO nominaDao;
	
	@Autowired
	private ParametrosLegalesDAO parametrosLegalesDao;
	

}
