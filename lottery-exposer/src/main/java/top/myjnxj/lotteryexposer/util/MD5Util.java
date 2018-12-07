package top.myjnxj.lotteryexposer.util;

import org.springframework.util.DigestUtils;
import top.myjnxj.lotteryexposer.po.IpUser;

/**
 * @ClassName MD5Util
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/12/6 21:48
 * @Version 1.0.0
 **/
public class MD5Util {
    private static String salt="###";
    private static final String FIRSTSALT="xyj1209";
    public static String generateMD5(String ip) {
        String base = ip + FIRSTSALT+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    public static boolean checkIpMD5(String md5, IpUser ipUser) {
        if (md5 == null || ipUser == null) {
            return false;
        }
        return md5.equals(generateMD5(ipUser.getIp()));
    }

    public static void setSalt(String salt1) {
        salt=salt1;
    }
}
