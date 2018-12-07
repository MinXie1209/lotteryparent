package top.myjnxj.lotteryexposer.service;

import top.myjnxj.lotteryexposer.po.IpUser;
import top.myjnxj.lotteryexposer.po.Result;

public interface ExposerService {
    /**
     * 传入用户信息，获取暴露信息
     * @param user
     * @return
     */
    Result exposer(IpUser user);
}
