package cn.jiuling.vehicleinfosys2.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	public static Logger logger = Logger.getLogger(PropertiesUtils.class);

	public static String get(String key) {
		String value = "";
		Properties p = new Properties();
		InputStream fis = null;
		try {
			URL u = PropertiesUtils.class.getClassLoader().getResource("parameter.properties");
			fis = new FileInputStream(u.getPath());
			p.load(new InputStreamReader(fis, "utf-8"));
			value = p.getProperty(key);
		} catch (Exception e) {
			logger.error("读取属性文件出错!!", e);
		}
		return value;
	}

	public static void main(String[] args) throws IOException {
		String hh = PropertiesUtils.get("vqdownload.newVersion");
		System.out.println(hh);
	}

}
