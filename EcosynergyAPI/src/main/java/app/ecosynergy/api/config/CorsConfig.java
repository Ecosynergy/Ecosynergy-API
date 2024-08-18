package app.ecosynergy.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:8888");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:80");
        config.addAllowedOrigin("https://ecosynergybr.com");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://ecosynergybr.com.s3-website-us-east-1.amazonaws.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
