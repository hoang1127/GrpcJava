package gash.router.redis;

import gash.router.container.RoutingConf;
import redis.clients.jedis.Jedis;

public class RedisDBServer {

    private Jedis jedis;
    private static RedisDBServer instanceRedis;

    private RedisDBServer(){

        this.jedis = new Jedis(RoutingConf.redis, 6379);
    }

    public static RedisDBServer getInstance(){

        if(instanceRedis == null){

            instanceRedis = new RedisDBServer();
        }
        return instanceRedis;
    }

    public Jedis getjedis(){
        
        return jedis;
    }
}
