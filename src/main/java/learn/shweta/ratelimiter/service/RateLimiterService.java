package learn.shweta.ratelimiter.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private DefaultRedisScript<Long> script;

    public RateLimiterService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init(){
        script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("rate_limiter.lua")));
    }

    public boolean isAllowed(String clientId, int capacity, int refillRate){
        String key = "rate:" + clientId;
        String now = String.valueOf((Instant.now().getEpochSecond()));

        Long result = redisTemplate.execute(
                script,
                Collections.singletonList(key),
                String.valueOf(capacity),
                String.valueOf(refillRate),
                now
        );

        System.out.println("DEBUG: Raw response from Redis Server Engine ---> " + result);

        return result != null && result == 1;
    }
}
