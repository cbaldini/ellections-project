package net.ellections.services;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
            expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
        log.error("Login failed from {} | {} attempts left.", key, MAX_ATTEMPT - attempts);
    }

    public boolean isBlocked(String key) {
        try {
            if (attemptsCache.get(key) >= MAX_ATTEMPT) {
                log.error("Login attempt from blocked IP {}", key);
                return true;
            } else {
                return false;
            }
        } catch (ExecutionException e) {
            return false;
        }
    }
}
