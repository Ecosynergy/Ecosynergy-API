package app.ecosynergy.api.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    @GetMapping("/home/")
    public void redirect(HttpServletResponse response) throws IOException {
        String deepLink = "ecosynergy://home";

        String fallbackUrl = "http://ecosynergybr.com.s3-website-us-east-1.amazonaws.com/";

        String html = """
            <html>
            <head>
                <title>Redirecionando...</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body>
                <p>Redirecionando para o aplicativo...</p>
                <script>
                    const deepLink = '%s';
                    const fallbackUrl = '%s';

                    window.location.href = deepLink;

                    setTimeout(() => {
                        window.location.href = fallbackUrl;
                    }, 2000);
                </script>
            </body>
            </html>
            """.formatted(deepLink, fallbackUrl);

        response.setContentType("text/html");
        response.getWriter().write(html);
    }
}

