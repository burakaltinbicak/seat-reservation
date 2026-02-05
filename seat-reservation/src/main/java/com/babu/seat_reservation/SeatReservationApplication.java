package com.babu.seat_reservation;

import com.babu.seat_reservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SeatReservationApplication {

	@Autowired
	private SeatService seatService;

	public static void main(String[] args) {
		SpringApplication.run(SeatReservationApplication.class, args);
	}

	// Uygulama ayağa kalkınca bu kod çalışır
	@Bean
	public CommandLineRunner demoData() {
		return args -> {
			seatService.initDemoData(); // Koltukları oluştur
		};
	}

}