package top.myjnxj.lotteryexposer.po;

import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName IpUser
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/11/23 0:03
 * @Version 1.0.0
 **/
public class IpUser implements Serializable {
    private String ip;
    private long timestamp;
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IpUser(String ip) {
        this.ip = ip;
    }

    public IpUser(String ip, long timestamp) {
        this.ip=ip;
        this.timestamp=timestamp;
    }

    public IpUser() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IpUser)) return false;
        IpUser ipUser = (IpUser) o;
        return timestamp == ipUser.timestamp &&
                Objects.equals(ip, ipUser.ip);
    }

    @Override
    public String toString() {
        return "IpUser{" +
                "ip='" + ip + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(ip, timestamp);
    }
}
