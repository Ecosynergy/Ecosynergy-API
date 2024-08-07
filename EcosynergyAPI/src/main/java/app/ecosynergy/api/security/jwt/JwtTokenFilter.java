package app.ecosynergy.api.security.jwt;

import app.ecosynergy.api.exceptions.InvalidJwtAuthenticationException;
import app.ecosynergy.api.security.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private final JwtTokenProvider tokenProvider;

    private final List<String> publicEndpoints;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider tokenProvider, SecurityProperties securityProperties) {
        this.publicEndpoints = securityProperties.getPublicEndpoints();
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();

        boolean isPublicEndpoint = publicEndpoints.stream().anyMatch(path::startsWith);

        if(isPublicEndpoint) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = tokenProvider.resolveToken((HttpServletRequest) request);

            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (InvalidJwtAuthenticationException ex) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            response.getWriter().write("{\"timestamp\": \"" + timestamp + "\", \"message\": \"" + ex.getMessage() + "\", \"path\": \"" + path + "\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
