package dev.fullstackcode.eis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

public class ApplicationLocalDevelopment {

    public static void main(String args[]) {
        SpringApplication.from(Application::main)
                .with(DevContainersConfiguration.class)
                .run(args);
    }

}

@TestConfiguration
class DevContainersConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresDBContainer() {
        return new PostgreSQLContainer<>("postgres:13.2");
    }

}
