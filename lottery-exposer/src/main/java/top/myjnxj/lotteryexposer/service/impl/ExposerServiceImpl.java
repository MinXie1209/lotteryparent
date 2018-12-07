package top.myjnxj.lotteryexposer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.myjnxj.lotteryexposer.enums.ResultEnum;
import top.myjnxj.lotteryexposer.mapper.PrizeMapper;
import top.myjnxj.lotteryexposer.po.Exposer;
import top.myjnxj.lotteryexposer.po.IpUser;
import top.myjnxj.lotteryexposer.po.Prize;
import top.myjnxj.lotteryexposer.po.Result;
import top.myjnxj.lotteryexposer.service.ExposerService;
import top.myjnxj.lotteryexposer.util.MD5Util;
import top.myjnxj.lotteryexposer.util.ResultUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ExposerServiceImpl
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/12/6 21:40
 * @Version 1.0.0
 **/
@Service
public class ExposerServiceImpl implements ExposerService {
    @Autowired
    PrizeMapper prizeMapper;
    @Override
    public Result exposer(IpUser user) {
        /**
         * 1. 获取数据库抽奖的开始时间和结束时间
         * 2.判断系统当前时间
         * 3.如果不在时间范围内，不暴露抽奖接口
         * 4.如果在抽奖允许访问内，暴露接口
         */
        List<Prize> prizes = prizeMapper.selectByExample(null);
        if (prizes.size() > 0) {
            Prize prize = prizes.get(0);
            Date startTime = prize.getStartTime();
            Date endTime = prize.getEndTime();
            Date now = new Date();
            if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
                return ResultUtils.success(new Exposer(false, startTime.getTime(), endTime.getTime(), now.getTime()));
            } else {
                String md5 = MD5Util.generateMD5(user.getIp());
                return ResultUtils.success(new Exposer(true, md5));
            }
        }
        return ResultUtils.error(ResultEnum.NOT_PRIZE);
    }

}
