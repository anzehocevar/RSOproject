package si.fri.rso.skupina06.api.v1.viri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"si.fri.rso.skupina06"})
public class KeycloakAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeycloakAuthApplication.class, args);
    }
}
