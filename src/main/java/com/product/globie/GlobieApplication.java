package com.product.globie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.product.globie.mapper")
public class GlobieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobieApplication.class, args);
	}

}
