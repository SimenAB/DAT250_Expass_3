package no.hvl.dat250.pollapp;

import redis.clients.jedis.UnifiedJedis;

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

}
