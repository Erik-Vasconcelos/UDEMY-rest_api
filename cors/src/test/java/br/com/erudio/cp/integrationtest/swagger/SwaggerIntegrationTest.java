package br.com.erudio.cp.integrationtest.swagger;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.erudio.cp.configs.TestConfigs;
import br.com.erudio.cp.integrationtest.testcontainer.AbstractIntegrationTest;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void showDisplaySwagerUiPage() {
		var content = RestAssured.given().basePath("/swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when().get()
				.then().statusCode(200)
				.extract().body().asString();
		
		assertTrue(content.contains("Swagger UI"));
	}

}
