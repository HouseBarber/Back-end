package com.projetoIntegradorII.HouseBarber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = {"com.projetoIntegradorII.HouseBarber"}, exclude = {SecurityAutoConfiguration.class })
@RequiredArgsConstructor
@Slf4j
@EnableSwagger2
public class HouseBarberApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseBarberApplication.class, args);
	}

}
