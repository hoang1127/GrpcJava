package gash.router.redis;

import gash.router.container.RoutingConf;

/**
 * Created by Nguyen on 4/12/18.
 */

import redis.clients.jedis.Jedis;

public class RedisServer {
    private Jedis localhostJedis;
    private static RedisServer instance;
    private RedisServer(){

        this.localhostJedis = new Jedis(RoutingConf.redis,6379);
    }

    public static RedisServer getInstance(){
        if(instance == null){
            instance = new RedisServer();
        }
        return instance;
    }

    public Jedis getLocalhostJedis(){
        return localhostJedis;
    }
}
