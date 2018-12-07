package top.myjnxj.lotteryexposer.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import top.myjnxj.lotteryexposer.enums.ResultEnum;
import top.myjnxj.lotteryexposer.exception.ResultException;
import top.myjnxj.lotteryexposer.po.IpUser;
import top.myjnxj.lotteryexposer.po.Result;
import top.myjnxj.lotteryexposer.service.ExposerService;
import top.myjnxj.lotteryexposer.util.TokenUtil;

/**
 * @ClassName ExposerController
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/12/6 21:33
 * @Version 1.0.0
 **/
@RestController
public class ExposerController {
    @Autowired
    ExposerService exposerService;
    /**
     * 暴露抽奖接口
     *
     * @return
     */
    @GetMapping("/api/exposer")
    public Result exposer(@RequestHeader(value = "token") String token) throws Exception {
        //解析token获取用户信息
        String userJson = null;
        try {
            userJson = TokenUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new ResultException(ResultEnum.TOKEN_ERROR);
        }
        JSONObject jsonObject = JSONObject.parseObject(userJson);
        IpUser user = JSONObject.toJavaObject(jsonObject, IpUser.class);
        System.out.println(user.toString());
        return exposerService.exposer(user);
    }
}
