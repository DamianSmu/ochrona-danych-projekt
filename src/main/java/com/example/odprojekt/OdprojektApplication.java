package com.example.odprojekt;

import com.example.odprojekt.entity.User;
import com.example.odprojekt.repository.UserRepository;
import com.example.odprojekt.security.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class OdprojektApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdprojektApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder encoder) {

		return args -> {
			if (!userRepository.findByUsername("damian").isPresent()) {
				Set<RoleEnum> roles = new HashSet<>();
				roles.add(RoleEnum.USER);
				User user = new User(
						"damian",
						"damian@damian.pl",
						encoder.encode("password123"),
						roles
				);
				userRepository.save(user);
			}

		};

	}
}
