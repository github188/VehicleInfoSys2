package cn.jiuling.vehicleinfosys2.listener;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
/**
 * tomcate监听器
 * 
 * @author daixiaowei
 * @version 2.0.2.3
 */
public class MyTomcateListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(MyTomcateListener.class);
	
	/**
	 * 当创建ServletContext触发此方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		logger.info("设定PictureServerHost、serverIp和activemq.brokerUrl为本机IP");
		
		//设定PictureServerHost、serverIp和activemq.brokerUrl为本机IP
		PropertiesUtils.setPictureServerHostIp();
		
		//自动创建uploadPath和resultPath目录
		PropertiesUtils.setVehicleDirectory();
	}
	
	/**
	 * 当销毁ServletContext触发此方法(此方法预留)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		

	}

}
