package top.myjnxj.lotteryexposer;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import org.springframework.data.redis.core.RedisTemplate;
import top.myjnxj.lotteryexposer.util.MD5Util;
import top.myjnxj.lotteryexposer.util.SpringUtil;

@MapperScan("top.myjnxj.lotteryexposer.mapper")
@EnableCaching
@SpringBootApplication
public class LotteryExposerApplication {
    private static final Logger logger = LoggerFactory.getLogger(LotteryExposerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LotteryExposerApplication.class, args);
        //TODO 生成salt,存储在redis中以供lootery-core模块获取。
        String salt=""+System.currentTimeMillis();
        MD5Util.setSalt(salt);
        //存储在redis,
        RedisTemplate<String,String> redisTemplate= (RedisTemplate<String, String>) SpringUtil.getBean("redisTemplate");
        redisTemplate.opsForValue().set("_salt",salt);
        logger.info("salt:"+salt);

    }
}
