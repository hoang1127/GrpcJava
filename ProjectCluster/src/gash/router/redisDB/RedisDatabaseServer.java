package gash.router.redis;

import gash.router.routingConfiguration.RoutingConf;
import redis.clients.jedis.Jedis;

public class RedisDatabaseServer {

    private Jedis jedis;
    private static RedisDatabaseServer instanceRedis;

    private RedisDatabaseServer(){

        this.jedis = new Jedis(RoutingConf.redis, 6379);
    }

    public static RedisDatabaseServer getInstance(){

        if(instanceRedis == null){

            instanceRedis = new RedisDatabaseServer();
        }
        return instanceRedis;
    }

    public Jedis getjedis(){
        return jedis;
    }
}
