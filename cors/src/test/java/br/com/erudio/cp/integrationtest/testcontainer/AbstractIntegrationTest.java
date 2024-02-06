package br.com.erudio.cp.integrationtest.testcontainer;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Integration.class)
public class AbstractIntegrationTest {

	static class Integration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		static PostgreSQLContainer<?> postgre = new PostgreSQLContainer<>("postgres:13");

		private static void startContainers() {
			Startables.deepStart(Stream.of(postgre)).join();
		}

		private static Map<String, String> createConnectionConfiguration() {
			return Map.of("spring.datasource.url", postgre.getJdbcUrl(),
					"spring.datasource.username", postgre.getUsername(),
					"spring.datasource.password", postgre.getPassword());
		}

		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();

			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testContainers = new MapPropertySource("testcontainers",
					(Map) createConnectionConfiguration());

			environment.getPropertySources().addFirst(testContainers);
		}

	}

}
