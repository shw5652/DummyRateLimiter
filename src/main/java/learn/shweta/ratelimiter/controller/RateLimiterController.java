package learn.shweta.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    @GetMapping("/api/resource")
    public String getProtectedData() {
        return "{\"message\": \"Success! You accessed the highly secure data.\"}";
    }
}
