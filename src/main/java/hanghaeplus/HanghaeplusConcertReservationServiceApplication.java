package hanghaeplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeplusConcertReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeplusConcertReservationServiceApplication.class, args);
    }

}
