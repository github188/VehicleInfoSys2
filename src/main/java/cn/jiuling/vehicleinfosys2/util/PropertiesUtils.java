package cn.jiuling.vehicleinfosys2.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

public class PropertiesUtils {
    public static Logger logger = Logger.getLogger(PropertiesUtils.class);
    public static final String PARAM_NAME = "parameter.properties";

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String resource) {
        String value = "";
        Properties p = new Properties();
        InputStream fis = null;
        InputStreamReader reader = null;
        URL u = null;
        try {
            ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
            if (resource == null) {
                u = classLoader.getResource(PARAM_NAME);
            } else {
                u = classLoader.getResource(resource);
            }
            fis = new FileInputStream(u.getPath());
            reader = new InputStreamReader(fis, "utf-8");
            p.load(reader);
            value = p.getProperty(key);
        } catch (Exception e) {
            logger.error("读取属性文件出错!!", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
        return value;
    }

    public static void main(String[] args) throws IOException {
        String hh = PropertiesUtils.get("vqdownload.newVersion");
        System.out.println(hh);
    }

    /**
     * 设定PictureServerHost、serverIp和activemq.brokerUrl为本机IP
     */
    public static void setPictureServerHostIp() {
        InputStream fis = null;
        InputStreamReader in = null;
        FileOutputStream out = null;
        try {
            //使用apache-commons-configuration包，避免修改properties后注释无法保存以及顺序错乱的问题
            URL u = PropertiesUtils.class.getClassLoader().getResource("serverIp.properties");

            fis = new FileInputStream(u.getPath());
            PropertiesConfiguration p = new PropertiesConfiguration();
            in = new InputStreamReader(fis, "utf-8");
            p.load(in);
            //获取本机ip
            String ip = InetAddress.getLocalHost().getHostAddress();
            //图片服务器url
            StringBuffer pictureServerHost = new StringBuffer();
            pictureServerHost.append("http://");
            pictureServerHost.append(ip);
            pictureServerHost.append(":8081");

            p.setProperty("PictureServerHost", pictureServerHost.toString());
            p.setProperty("serverIp", ip);
            p.setProperty("activemq.brokerUrl", "tcp://" + ip + ":6616");

            out = new FileOutputStream(u.getPath());
            p.save(out, "utf-8");

        } catch (UnknownHostException e) {
            logger.error("获取本机ip异常", e);
        } catch (ConfigurationException e) {
            logger.error("读取属性文件出错!", e);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件!", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码!", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }

    }

    /**
     * 自动创建uploadPath和resultPath目录
     */
    public static void setVehicleDirectory() {
        //获取文件上传目录
        String uploadPath = get("uploadPath");
        //获取识别结果目录
        String resultPath = get("resultPath");

        File uploadFile = new File(uploadPath);
        File resultFile = new File(resultPath);

        //创建目录
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        if (!resultFile.exists()) {
            resultFile.mkdirs();
        }
    }

}
