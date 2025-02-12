package fr.eni.eni_cda_enchere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EniCdaEnchereApplication {

    public static void main(String[] args) {
        SpringApplication.run(EniCdaEnchereApplication.class, args);
    }
}
