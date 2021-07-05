package com.bank.credit.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Spring Boot application, which consume in built tomcat server to run.
 */
@SpringBootApplication
public class CreditCardApplication {
	public static final Logger LOG = LoggerFactory.getLogger(CreditCardApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CreditCardApplication.class, args);
		LOG.info("Credit Card Application Started");
	}

}
