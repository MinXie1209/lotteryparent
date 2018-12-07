package top.myjnxj.lotterylogin.controller;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.myjnxj.lotterylogin.po.IpUser;
import top.myjnxj.lotterylogin.po.Result;
import top.myjnxj.lotterylogin.util.ResultUtils;
import top.myjnxj.lotterylogin.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/11/8 11:30
 * @Version 1.0.0
 **/
@CrossOrigin
@Controller
public class IpUserController {

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpServletRequest request;
@ResponseBody
    @GetMapping("/api/login")
    public Result test() throws Exception {
        String ip = getIpAddr(request);
        String token = TokenUtil.createJWT(JSONObject.toJSONString(new IpUser(ip,System.currentTimeMillis())), 24 * 60 * 60 * 1000);
        logger.info("ip:" + ip);
        return ResultUtils.success(token);
    }

    /**
     * @Description: 获取客户端IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
