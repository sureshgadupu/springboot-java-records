package dev.fullstackcode.eis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ContextConfiguration(classes=DevContainersConfiguration.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@TestConfiguration
	class DevContainersConfiguration {
		@Bean
		@ServiceConnection
		PostgreSQLContainer<?> postgresDBContainer() {
			return new PostgreSQLContainer<>("postgres:13.2");
		}

	}

}
