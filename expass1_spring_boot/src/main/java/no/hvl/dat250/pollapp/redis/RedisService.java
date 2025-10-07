package no.hvl.dat250.pollapp.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.UnifiedJedis;
import jakarta.annotation.PreDestroy;

@Service
public class RedisService {
    private final UnifiedJedis jedis;

    // connect to the server
    public RedisService() {
        this.jedis = new UnifiedJedis("redis://localhost:6379");
    }

    //set value with key
    public void setValue(String key, String value) {
        jedis.set(key, value);
    }

    //get value with key
    public String getValue(String key) {
        return jedis.get(key);
    }

    @PreDestroy
    public void destroy() {
        jedis.close();
    }

}
