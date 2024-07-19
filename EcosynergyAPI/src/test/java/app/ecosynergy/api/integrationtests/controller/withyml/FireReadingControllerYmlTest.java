package app.ecosynergy.api.integrationtests.controller.withyml;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.controller.withyml.mapper.YmlMapper;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.FireReadingVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.pagedmodels.PagedModelFireReading;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FireReadingControllerYmlTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static YmlMapper mapper;

    private static Integer countReadings;

    private static FireReadingVO fireReading;
    @BeforeAll
    static void setup(){
        mapper = new YmlMapper();
        fireReading = new FireReadingVO();
    }

    @Test
    @Order(1)
    public void authorization(){
        AccountCredentialsVO credentials = new AccountCredentialsVO("anderson", "admin123");
        String accessToken = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .body(credentials, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class, mapper)
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
    public void testCreate() {
        mockReading();

        fireReading = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .body(fireReading, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(FireReadingVO.class, mapper);

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
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .body(fireReading, mapper)
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
    public void testFindById() {
        FireReadingVO resultVO = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .header("Time-Zone", "America/Sao_Paulo")
                .pathParam("id", fireReading.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(FireReadingVO.class, mapper);

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
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
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
    public void testFindAll() {
        PagedModelFireReading pagedModel = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PagedModelFireReading.class, mapper);

        List<FireReadingVO> fireReadings = pagedModel.getContent();

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
        var content = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
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
        var content = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertTrue(content.contains("links:- rel: \"self\"href: \"http://localhost:8888/api/fireReading/v1/" + fireReading.getId() + "\""));
        assertTrue(content.contains("links:- rel: \"self\"href: \"http://localhost:8888/api/fireReading/v1?page=0&limit=5&direction=timestamp%3A%20ASC\""));
        assertTrue(content.contains("page:size: 5totalElements: " + countReadings + "totalPages: 1number: 0"));
    }

    private void mockReading(){
        fireReading.setFire(true);
    }
}
