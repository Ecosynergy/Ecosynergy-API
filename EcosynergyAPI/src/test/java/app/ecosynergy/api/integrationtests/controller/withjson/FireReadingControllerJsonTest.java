package app.ecosynergy.api.integrationtests.controller.withjson;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.FireReadingVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.wrappers.WrapperFireReadingVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class FireReadingControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static Integer countReadings;

    private static FireReadingVO fireReading;
    @BeforeAll
    static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());

        fireReading = new FireReadingVO();
    }

    @Test
    @Order(1)
    public void authorization(){
        AccountCredentialsVO credentials = new AccountCredentialsVO("anderson", "admin123");
        String accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/fireReading/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(2)
    public void testCreate() throws IOException {
        mockReading();

        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(fireReading)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        fireReading = objectMapper.readValue(content, FireReadingVO.class);

        assertNotNull(fireReading);
        assertNotNull(fireReading.getId());
        assertNotNull(fireReading.getTimestamp());
        assertNotNull(fireReading.getFire());

        assertTrue(fireReading.getFire());
    }

    @Test
    @Order(3)
    public void testCreateWithWrongOrigin(){
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .body(fireReading)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(4)
    public void testFindById() throws IOException {
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header("Time-Zone", "America/Sao_Paulo")
                .pathParam("id", fireReading.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        FireReadingVO resultVO = objectMapper.readValue(content, FireReadingVO.class);

        assertNotNull(resultVO);
        assertNotNull(resultVO.getId());
        assertNotNull(resultVO.getTimestamp());
        assertNotNull(resultVO.getFire());

        assertEquals(fireReading.getId(), resultVO.getId());
        assertEquals(fireReading.getTimestamp(), resultVO.getTimestamp());
        assertTrue(resultVO.getFire());
    }

    @Test
    @Order(5)
    public void testFindByIdWithWrongOrigin(){
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .pathParam("id", fireReading.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperFireReadingVO wrapper = objectMapper.readValue(content, WrapperFireReadingVO.class);

        List<FireReadingVO> fireReadings = wrapper.getEmbedded().getFireReadingVOList();

        assertNotNull(fireReadings);

        fireReadings.forEach(reading -> {
            assertNotNull(fireReading.getId());
            assertNotNull(fireReading.getTimestamp());
            assertNotNull(fireReading.getFire());
        });

        countReadings = fireReadings.size();
    }

    @Test
    @Order(7)
    public void testFindAllWithWrongOrigin() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(8)
    public void testHATEOAS() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/fireReading/v1/" + fireReading.getId() + "\"}}}"));

        assertTrue(content.contains("\"page\":{\"size\":5,\"totalElements\":" + countReadings + ",\"totalPages\":1,\"number\":0}}"));
    }

    private void mockReading(){
        fireReading.setFire(true);
    }
}
