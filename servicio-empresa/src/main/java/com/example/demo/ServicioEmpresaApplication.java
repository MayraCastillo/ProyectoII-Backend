package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableEurekaClient
@EnableFeignClients
public class ServicioEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioEmpresaApplication.class, args);
	}
	
	/*@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
	  CharacterEncodingFilter filter = new CharacterEncodingFilter();
	  filter.setEncoding("UTF-8"); // use your preferred encoding
	  filter.setForceEncoding(true); // force the encoding

	  FilterRegistrationBean<CharacterEncodingFilter> registrationBean =
	    new FilterRegistrationBean<>(filter); // register the filter
	  registrationBean.addUrlPatterns("/*"); // set preferred url
	  return registrationBean;
	}*/

}
