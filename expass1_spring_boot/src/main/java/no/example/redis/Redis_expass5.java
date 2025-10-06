package no.example.redis;

import redis.clients.jedis.UnifiedJedis;

public class Redis_expass5 {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        // Simple test
        jedis.set("testkey", "hello from redis!");
        String value = jedis.get("testkey");
        System.out.println("Fetched: " + value);

        jedis.close();
    }
}