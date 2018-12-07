package top.myjnxj.lotterycore.util;


import top.myjnxj.lotterycore.po.Lottery;

/**
 * @ClassName LotteryUtil
 * @Description 抽奖号码转换为抽奖等级
 * @Author 江南小俊
 * @Date 2018/11/15 11:40
 * @Version 1.0.0
 **/
public class LotteryUtil {
    public static String numberToLevel(int number, Lottery lottery) {
        if ((number > lottery.getSpecialAndFirstLine())) {
            return "特等奖";
        } else if (number > lottery.getFirstAndSecondLine()) {
            return "一等奖";
        } else if (number > lottery.getSecondAndThirdLine()) {
            return "二等奖";
        } else {
            return "三等奖";
        }
    }
}
