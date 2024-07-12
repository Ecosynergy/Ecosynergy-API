package app.ecosynergy.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Ecosynergy API")
                        .description("")
                        .version("v1")
                        .termsOfService("")
                        .license(new License()
                                .name("GitHub")
                                .url("https://github.com/anderson-rodrigues-dev")
                        )
                );
    }
}
