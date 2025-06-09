package gr.aueb.cf.hotelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HotelBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingSystemApplication.class, args);
	}

}
