package com.bbek.BbekServiceA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BbekServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbekServiceAApplication.class, args);
	}

}
