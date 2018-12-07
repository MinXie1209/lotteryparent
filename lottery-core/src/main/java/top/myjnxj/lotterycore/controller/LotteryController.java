package top.myjnxj.lotterycore.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.myjnxj.lotterycore.enums.ResultEnum;
import top.myjnxj.lotterycore.exception.ResultException;
import top.myjnxj.lotterycore.po.IpUser;
import top.myjnxj.lotterycore.po.Lottery;
import top.myjnxj.lotterycore.util.LotteryUtil;
import top.myjnxj.lotterycore.util.MD5Util;
import top.myjnxj.lotterycore.util.ResultUtils;
import top.myjnxj.lotterycore.util.TokenUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LotteryController
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/12/6 22:47
 * @Version 1.0.0
 **/

@CrossOrigin
@RestController
public class LotteryController {

    @Autowired
    private Lottery lottery;
    @Autowired
    RedisTemplate<IpUser, Integer> ipUserRedisTemplate;
    /**
     * 奖池号码max,min为0
     */
    private static AtomicInteger prizeNum;
    /**
     * 是否还剩奖项
     */
    private static boolean isHasPrizeNumber = true;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static ConcurrentHashMap<IpUser, Integer> users = new ConcurrentHashMap<>(1 << 11);

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 抽奖接口
     * 1.判断token正确
     * 2.判断MD5是否正确
     * 3.判断用户中奖
     * 4.抽奖
     *
     * @return
     */
    @RequestMapping("/api/{md5}/lottery")
    public Object lottery(@RequestHeader(value = "token") String token, @PathVariable(value = "md5") String md5) throws Exception {

        String userJson = null;

        try {
            userJson = TokenUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new ResultException(ResultEnum.TOKEN_ERROR);
        }
        JSONObject jsonObject = JSONObject.parseObject(userJson);
        IpUser user = JSONObject.toJavaObject(jsonObject, IpUser.class);
        if (!MD5Util.checkIpMD5(md5, user)) {
            return ResultUtils.error(ResultEnum.ERROR_MD5);
        }
        Integer oldNum = ipUserRedisTemplate.opsForValue().get(user);
        System.out.println(oldNum);
        if (oldNum != null) {
            return ResultUtils.success(LotteryUtil.numberToLevel(oldNum, lottery));
        } else {
            initPrizeSize();
            if (isHasPrizeNumber) {
                int readPrizeNum = prizeNum.decrementAndGet();
                int myPrizeNum = readPrizeNum + 1;
                if (readPrizeNum >= 0) {
                    logger.info("ip:" + user.getIp()+",抽到的号码为：" + myPrizeNum);
                    //只有中奖才会存起来
                    ipUserRedisTemplate.opsForValue().set(user, myPrizeNum);
                    return ResultUtils.success(LotteryUtil.numberToLevel(myPrizeNum, lottery));
                } else {

                    isHasPrizeNumber = false;
                    return ResultUtils.success("抱歉，您没中奖");
                }
            } else {
                return ResultUtils.success("抱歉，您没中奖");
            }
        }

    }


    @RequestMapping("/api/test/lottery")
    public Object testLottery() throws Exception {
        //  初始化奖池
        initPrizeSize();
        if (isHasPrizeNumber) {
            int readPrizeNum = prizeNum.decrementAndGet();
            int myPrizeNum = readPrizeNum + 1;
            if (readPrizeNum >= 0) {
                logger.info("抽到的号码为：" + myPrizeNum);
                return ResultUtils.success(LotteryUtil.numberToLevel(myPrizeNum, lottery));
            } else {
                isHasPrizeNumber = false;
                return ResultUtils.success("抱歉，您没中奖");
            }
        } else {
            return ResultUtils.success("抱歉，您没中奖");
        }
    }

    /**
     * 用于初始化奖池大小
     */
    private void initPrizeSize() {
        if (prizeNum == null) {
            lock.lock();
            try {
                if (prizeNum == null) {
                    prizeNum = new AtomicInteger(lottery.getPrizeSize());
                }
            } finally {
                lock.unlock();
            }
        }

    }
}