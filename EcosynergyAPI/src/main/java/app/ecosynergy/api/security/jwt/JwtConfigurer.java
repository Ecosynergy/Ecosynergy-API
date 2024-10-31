package app.ecosynergy.api.security.jwt;

import app.ecosynergy.api.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider tokenProvider;
    private final SecurityProperties securityProperties;

    @Autowired
    public JwtConfigurer(JwtTokenProvider tokenProvider, SecurityProperties securityProperties) {
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider, securityProperties);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
