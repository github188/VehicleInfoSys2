package cn.jiuling.vehicleinfosys2.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2015/12/3.
 */
public class TCPIPUtil {

    protected static Logger logger = Logger.getLogger(TCPIPUtil.class);
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isBlank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(ip)) {
            StringBuffer requestURL = request.getRequestURL();
            if(requestURL.indexOf("localhost")>-1) {
                //获取本机ip
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            } else {
                ip = request.getRemoteAddr();
            }
        }
        if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
            String[] ipArray = StringUtils.split(ip, ",");
            ip = ipArray[0];
        }
        logger.info("客户端ＩＰ:" + ip);
        return ip;
    }

    private static boolean isBlank(String ip) {
        if (StringUtils.isBlank(ip) || "unkown".equalsIgnoreCase(ip) || ip.split("\\.").length != 4) {
            return true;
        }
        return false;
    }
}
