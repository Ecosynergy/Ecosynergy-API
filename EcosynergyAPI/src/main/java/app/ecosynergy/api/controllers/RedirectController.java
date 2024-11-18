package app.ecosynergy.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    @GetMapping("/{inviteId}")
    public ResponseEntity<Void> redirect(@PathVariable String inviteId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        String appLink = "ecosynergy://invite?inviteId=" + inviteId;

        String fallbackLink = "http://ecosynergybr.com.s3-website-us-east-1.amazonaws.com/";

        System.out.println(userAgent);

        if (userAgent != null && userAgent.contains("Mobile")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(appLink))
                    .build();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(fallbackLink))
                .build();
    }
}

