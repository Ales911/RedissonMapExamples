package by.lyofa.redissonmapexamples;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonMapExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        // RedissonClient redisson = Redisson.create();

        Config config = new Config();
        config.useSingleServer()
                .setTimeout(1000000)
                // .setAddress("redis://127.0.1.1:6379");
                .setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        RMap<String, Integer> map = redisson.getMap("myMap");
        
        System.out.println("map.keySet(): " +": " + map.keySet());

        System.out.println("a: " + map.get("a"));

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        System.out.println("contains(a) : " + map.containsKey("a"));
        System.out.println("c: " + map.get("c"));
        //Object updatedValue =  map.addAndGet("a", 32);

        System.out.println("map.valueSize(c): " + map.valueSize("c"));

        Set keys = new HashSet();
        keys.add("a");
        keys.add("b");
        keys.add("c");
        Map mapSlice = map.getAll(keys);

        // use read* methods to fetch all objects
        Set allKeys = map.readAllKeySet();
        Collection allValues = map.readAllValues();
        Set allEntries = map.readAllEntrySet();

        // use fast* methods when previous value is not required
        boolean isNewKey = map.fastPut("a", 100);
        boolean isNewKeyPut = map.fastPutIfAbsent("d", 33);
        long removedAmount = map.fastRemove("b");

        redisson.shutdown();
    }
}
