package top.myjnxj.lotterycore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import top.myjnxj.lotterycore.util.MD5Util;
import top.myjnxj.lotterycore.util.SpringUtil;

@EnableCaching
@SpringBootApplication
public class LotteryCoreApplication {
    private static final Logger logger = LoggerFactory.getLogger(LotteryCoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LotteryCoreApplication.class, args);
        RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) SpringUtil.getBean("redisTemplate");
        boolean noSalt=true;
        String salt =null;
        while (noSalt){
           salt= redisTemplate.opsForValue().get("_salt");
            if(salt==null){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                noSalt=false;
            }
        }


        MD5Util.setSalt(salt);
        logger.info("salt:"+salt);

    }
}
