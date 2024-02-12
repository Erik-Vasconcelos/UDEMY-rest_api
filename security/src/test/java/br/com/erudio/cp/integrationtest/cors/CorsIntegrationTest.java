package br.com.erudio.cp.integrationtest.cors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.cp.configs.TestConfigs;
import br.com.erudio.cp.integrationtest.testcontainer.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CorsIntegrationTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	private static PersonVO person;
	
	
	@BeforeAll
	public static void setup() {
		person = new PersonVO();
		
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //desabilita o erro para propriedades que vem a mais
	}
	
	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = RestAssured.given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(person)
				.when().post()
				.then().statusCode(200)
				.extract().body().asString();
		
		PersonVO createdPerson = mapper.readValue(content, PersonVO.class);
		person = createdPerson;
		
		
		assertNotNull(createdPerson);
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getPostalCode());

		assertTrue(createdPerson.getId() > 0);
		assertEquals("Richard", createdPerson.getFirstName());
		assertEquals("Stallman", createdPerson.getLastName());
		assertEquals("00000-000", createdPerson.getPostalCode());
	
	}
	
	@Test
	@Order(2)
	void testCreatedWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = RestAssured.given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(person)
				.when().post()
				.then().statusCode(403)
				.extract().body().asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	
	@Test
	@Order(3)
	void testFindPerson() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = RestAssured.given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
				.when().get("{id}")
				.then().statusCode(200)
				.extract().body().asString();
		
		PersonVO persistedPerson = mapper.readValue(content, PersonVO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getPostalCode());

		assertTrue(persistedPerson.getId() > 0);
		assertEquals("Richard", persistedPerson.getFirstName());
		assertEquals("Stallman", persistedPerson.getLastName());
		assertEquals("00000-000", persistedPerson.getPostalCode());
	
	}
	
	@Test
	@Order(4)
	void testFindByIdWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = RestAssured.given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
				.when().get("{id}")
				.then().statusCode(403)
				.extract().body().asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	
	
	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setPostalCode("00000-000");
	}

}
