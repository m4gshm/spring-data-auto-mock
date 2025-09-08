package test.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import test.jpa.repo.ClientRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = ClientRepository.class)
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
}
