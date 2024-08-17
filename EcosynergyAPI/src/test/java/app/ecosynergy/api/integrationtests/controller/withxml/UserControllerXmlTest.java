package app.ecosynergy.api.integrationtests.controller.withxml;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.UserVO;
import app.ecosynergy.api.integrationtests.vo.pagedmodels.PagedModelUser;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserControllerXmlTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static XmlMapper xmlMapper;

    private static Integer countUsers;

    private static UserVO user;

    @BeforeAll
    public static void setup() {
        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.registerModule(new Jackson2HalModule());
        
        user = new UserVO();
    }

    @Test
    @Order(1)
    public void authorization(){
        AccountCredentialsVO credentials = new AccountCredentialsVO("anderson", "admin123");
        String accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
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
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(2)
    public void testFindById() throws IOException {
        String result = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("id", 3)
                .when()
                .get("id/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        
        user = xmlMapper.readValue(result, UserVO.class);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertNotNull(user.getFullName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getGender());
        assertNotNull(user.getNationality());
        assertTrue(user.getEnabled());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());

        assertEquals(3, user.getId());
        assertEquals("testecontainerxml", user.getUsername());
        assertEquals("Anderson Rodrigues XML", user.getFullName());
        assertEquals("testecontainerxml@gmail.com", user.getEmail());
        assertEquals("Male", user.getGender());
        assertEquals("Brazilian", user.getNationality());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }

    @Test
    @Order(3)
    public void testFindByIdWithWrongOrigin(){
        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .pathParam("id", 3)
                .when()
                .get("id/{id}")
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
    public void testUpdate() throws IOException {
        user.setEmail("andiin@hotmail.com");
        String result = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("id", user.getId())
                .body(user)
                .when()
                .put("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        user = xmlMapper.readValue(result, UserVO.class);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertNotNull(user.getFullName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getGender());
        assertNotNull(user.getNationality());
        assertTrue(user.getEnabled());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());

        assertEquals(3, user.getId());
        assertEquals("testecontainerxml", user.getUsername());
        assertEquals("Anderson Rodrigues XML", user.getFullName());
        assertEquals("andiin@hotmail.com", user.getEmail());
        assertEquals("Male", user.getGender());
        assertEquals("Brazilian", user.getNationality());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }

    @Test
    @Order(5)
    public void testUpdateWithWrongOrigin(){
        user.setEmail("anderson.rod.dev@gmail.com");
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .pathParam("id", user.getId())
                .body(user)
                .when()
                .put("{id}")
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
    public void testFindAll() throws IOException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PagedModelUser pagedModel = xmlMapper.readValue(content, PagedModelUser.class);

        List<UserVO> users = pagedModel.getContent();

        assertNotNull(users);

        users.forEach(u -> {
            assertNotNull(u.getId());
            assertNotNull(u.getUsername());
            assertNotNull(u.getFullName());
            assertNotNull(u.getEmail());
            assertNotNull(u.getGender());
            assertNotNull(u.getNationality());
            assertTrue(u.getEnabled());
            assertTrue(u.getAccountNonExpired());
            assertTrue(u.getAccountNonLocked());
        });

        countUsers = users.size();
    }

    @Test
    @Order(7)
    public void testFindAllWithWrongOrigin() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
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
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .queryParams("page", 1, "limit", 10, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/user/v1/findId/1</href></links>"));
        assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/user/v1/findId/3</href></links>"));
        assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/user/v1/findId/4</href></links>"));
        assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/user/v1?page=0&amp;limit=10&amp;direction=fullName%3A%20ASC</href></links>"));
        assertTrue(content.contains("<page><size>10</size><totalElements>" + countUsers + "</totalElements><totalPages>1</totalPages><number>0</number></page>"));
    }

    @Test
    @Order(9)
    public void testDeleteWithWrongOrigin(){
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .pathParam("id", user.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(10)
    public void testDelete(){
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("id", user.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}
