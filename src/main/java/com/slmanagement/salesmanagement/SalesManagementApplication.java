package com.slmanagement.salesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//  @EntityScan(basePackages = {"com.slmanagement.salesmanagement.entities"})
//  @EnableJpaRepositories(basePackages = {"com.slmanagement.salesmanagement.repositories"})
//  @ComponentScan(basePackages = {"com.slmanagement.salesmanagement.services", "com.slmanagement.salesmanagement.controllers"})
@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class SalesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesManagementApplication.class, args);
	}

}
