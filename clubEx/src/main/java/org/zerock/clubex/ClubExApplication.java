package org.zerock.clubex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClubExApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubExApplication.class, args);
    }

}