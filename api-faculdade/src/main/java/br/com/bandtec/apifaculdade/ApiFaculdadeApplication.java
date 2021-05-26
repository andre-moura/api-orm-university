package br.com.bandtec.apifaculdade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiFaculdadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFaculdadeApplication.class, args);
	}

}
