package com.berkanterdogan.springsecuritylab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.berkanterdogan.springsecuritylab.domain"})
@EnableJpaRepositories(basePackages = "com.berkanterdogan.springsecuritylab.repository")
@SpringBootApplication
public class SpringSecurityLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityLabApplication.class, args);
    }

}
