package app.ecosynergy.api.integrationtests.controller.withyml;

import app.ecosynergy.api.configs.TestConfigs;
import app.ecosynergy.api.integrationtests.controller.withyml.mapper.YmlMapper;
import app.ecosynergy.api.integrationtests.testcontainers.AbstractIntegrationTest;
import app.ecosynergy.api.integrationtests.vo.AccountCredentialsVO;
import app.ecosynergy.api.integrationtests.vo.TokenVO;
import app.ecosynergy.api.integrationtests.vo.UserVO;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerYmlTest extends AbstractIntegrationTest {
    private static YmlMapper mapper;

    private static TokenVO tokenVO;
    private static UserVO user;

    @BeforeAll
    public static void setUp(){
        mapper = new YmlMapper();

        user = new UserVO();
    }

    @Test
    @Order(1)
    void testSignup() {
        mockUser();

        UserVO response = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .basePath("/auth/signup")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .body(user, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(UserVO.class, mapper);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getUsername());
        assertNotNull(response.getFullName());
        assertNotNull(response.getEmail());
        assertNotNull(response.getPassword());
        assertNotNull(response.getGender());
        assertNotNull(response.getNationality());
        assertTrue(response.getEnabled());
        assertTrue(response.getAccountNonExpired());
        assertTrue(response.getAccountNonLocked());
    }

    @Test
    @Order(2)
    void testSignin() {
        AccountCredentialsVO credentials = new AccountCredentialsVO(user.getUsername(), user.getPassword());
        tokenVO = given()
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
                .as(TokenVO.class, mapper);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    @Test
    @Order(3)
    void testRefreshToken() {
        tokenVO = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)
                        )
                )
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YAML)
                .accept(TestConfigs.CONTENT_TYPE_YAML)
                .pathParam("username", tokenVO.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class, mapper);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    private void mockUser(){
        user.setUsername("testeyml");
        user.setFullName("Fulano da Silva");
        user.setEmail("testeyml@gmail.com");
        user.setPassword("admin123");
        user.setGender("Male");
        user.setNationality("Brazilian");
    }
}
