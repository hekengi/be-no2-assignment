package org.example.beno2assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.beno2assignment.repository.jpa")
@EnableJdbcRepositories(basePackages = "org.example.beno2assignment.repository.jdbc") // ✅ 명시적으로 지정
public class BeNo2AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeNo2AssignmentApplication.class, args);

    }

}
