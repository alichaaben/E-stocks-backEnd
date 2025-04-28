package com.e_stock.BackEnd_E_stocks;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.e_stock.BackEnd_E_stocks.entity.InfoSociete;
import com.e_stock.BackEnd_E_stocks.repository.InfoSocieteRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class BackEndEStocksApplication {

	private final InfoSocieteRepository infoSocieteRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackEndEStocksApplication.class, args);
	}

		@Bean
	public CommandLineRunner initDatabase() {
		return args -> {
			// Vérifier si un societe  existe, sinon le créer
			Optional<InfoSociete> socOpt = infoSocieteRepository.findById(1L);
			socOpt.orElseGet(() -> {
				InfoSociete newSoc = new InfoSociete();
				newSoc.setNom("anonyme");
				newSoc.setAdresse("anonyme");
				newSoc.setTelephone("anonyme");
				return infoSocieteRepository.save(newSoc);
			});

				// AppUser adminUser = new AppUser();
				// adminUser.setUserName("admin");
				// adminUser.setEmail("admin@example.com");
				// adminUser.setTelephone("123456789");
				// adminUser.setMotDePasse(passwordEncoder.encode("ali1234"));
				// adminUser.setRole(adminRole);
				// adminUser.setProfileImage("default.png");
				// appUserRepository.save(adminUser);
				// System.out.println("Admin user created successfully");
		};
	}


}
