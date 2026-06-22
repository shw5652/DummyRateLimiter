package learn.shweta.ratelimiter.interceptor;

import learn.shweta.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    public RateLimiterInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();

        System.out.println("DEBUG: Incoming request from IP ---> " + clientIp);

        int maxBurstCapacity = 5;
        int refillRatePerSecond = 1;

        boolean allowed = rateLimiterService.isAllowed(clientIp, maxBurstCapacity, refillRatePerSecond);

        if (!allowed) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many requests. Rate limit exceeded.\"}");
            return false;
        }

        return true;
    }
}