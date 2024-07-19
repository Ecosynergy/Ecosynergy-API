package app.ecosynergy.api.integrationtests.controller.withjson;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.UserVO;
import app.ecosynergy.api.integrationtests.vo.wrappers.WrapperUserVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static Integer countUsers;

    private static UserVO user;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        user = new UserVO();
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
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", 2)
                .when()
                .get("findId/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        
        user = objectMapper.readValue(result, UserVO.class);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertNotNull(user.getFullName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getGender());
        assertNotNull(user.getNationality());
        assertTrue(user.getEnabled());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());

        assertEquals(2, user.getId());
        assertEquals("testecontainerjson", user.getUsername());
        assertEquals("Anderson Rodrigues JSON", user.getFullName());
        assertEquals("testecontainerjson@gmail.com", user.getEmail());
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
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .pathParam("id", 2)
                .when()
                .get("findId/{id}")
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
        user.setEmail("and.rt@hotmail.com");
        String result = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        user = objectMapper.readValue(result, UserVO.class);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertNotNull(user.getFullName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getGender());
        assertNotNull(user.getNationality());
        assertTrue(user.getEnabled());
        assertTrue(user.getAccountNonExpired());
        assertTrue(user.getAccountNonLocked());

        assertEquals(2, user.getId());
        assertEquals("testecontainerjson", user.getUsername());
        assertEquals("Anderson Rodrigues JSON", user.getFullName());
        assertEquals("and.rt@hotmail.com", user.getEmail());
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
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST)
                .body(user)
                .when()
                .put()
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
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 1, "limit", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperUserVO wrapper = objectMapper.readValue(content, WrapperUserVO.class);

        List<UserVO> users = wrapper.getEmbedded().getUserVOList();

        assertNotNull(users);

        users.forEach(u -> {
            assertNotNull(u.getId());
            assertNotNull(u.getUsername());
            assertNotNull(u.getFullName());
            assertNotNull(u.getEmail());
            assertNotNull(u.getPassword());
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
                .queryParams("page", 1, "limit", 10, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/user/v1/findId/2\"}}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/user/v1?page=0&limit=10&direction=fullName%3A%20ASC\"}}"));
        assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":" + countUsers + ",\"totalPages\":1,\"number\":0}}"));
    }

    @Test
    @Order(9)
    public void testDeleteWithWrongOrigin(){
        String content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
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
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", user.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}
