package app.ecosynergy.api.integrationtests.controller.withxml;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {
    private static XmlMapper xmlMapper;

    private static TokenVO tokenVO;
    private static UserVO user;

    @BeforeAll
    public static void setUp(){
        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.registerModule(new Jackson2HalModule());

        user = new UserVO();
    }

    @Test
    @Order(1)
    void testSignup() throws JsonProcessingException {
        mockUser();

        String response = given()
                .basePath("/auth/signup")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        UserVO vo = xmlMapper.readValue(response, UserVO.class);

        assertNotNull(vo);
        assertNotNull(vo.getId());
        assertNotNull(vo.getUsername());
        assertNotNull(vo.getFullName());
        assertNotNull(vo.getEmail());
        assertNotNull(vo.getPassword());
        assertNotNull(vo.getGender());
        assertNotNull(vo.getNationality());
        assertTrue(vo.getEnabled());
        assertTrue(vo.getAccountNonExpired());
        assertTrue(vo.getAccountNonLocked());
    }

    @Test
    @Order(2)
    void testSignin() {
        AccountCredentialsVO credentials = new AccountCredentialsVO(user.getUsername(), user.getPassword());
        System.out.println(user.getUsername() + " " + user.getPassword());
        tokenVO = given()
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
                .as(TokenVO.class);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    @Test
    @Order(3)
    void testRefreshToken() {
        tokenVO = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_XML)
                .accept(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("username", tokenVO.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    private void mockUser(){
        user.setUsername("testexml");
        user.setFullName("Fulano da Silva");
        user.setEmail("testexml@gmail.com");
        user.setPassword("admin123");
        user.setGender("Male");
        user.setNationality("Brazilian");
    }
}
