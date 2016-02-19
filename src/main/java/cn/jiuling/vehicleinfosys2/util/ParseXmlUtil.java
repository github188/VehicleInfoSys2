package cn.jiuling.vehicleinfosys2.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;



import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.jiuling.vehicleinfosys2.vo.MenusControlVo;

/**
 * 解析xml
 * @author daixiaowei
 *
 */
public class ParseXmlUtil {
	/**
	 * 解析菜单配置xml
	 */
	public static MenusControlVo parseMenusXml(){
		
		MenusControlVo result = new MenusControlVo();
		try {			
			//读取xml文件路径
			URL url = ParseXmlUtil.class.getClassLoader().getResource("menusControler.xml");
			InputStream is = new FileInputStream(url.getPath());
			
			//dom4j解析xml
			SAXReader reader = new SAXReader();
			Document document = reader.read(is);
			//获取文档根节点
			Element root = document.getRootElement();
			for(Iterator it =  root.elements("item").iterator();it.hasNext();){
				Element item = (Element)it.next();
				String id = item.attribute("id").getText();
				String hidden = item.attribute("hidden").getText();
				
				if("1".equals(id)){
					result.setMenu1(Boolean.valueOf(hidden));
				}
				if("2".equals(id)){
					result.setMenu2(Boolean.valueOf(hidden));
				}
				if("3".equals(id)){
					result.setMenu3(Boolean.valueOf(hidden));
				}				
				if("4".equals(id)){
					result.setMenu4(Boolean.valueOf(hidden));
				}
				if("5".equals(id)){
					result.setMenu5(Boolean.valueOf(hidden));
				}
				
			    if("4".equals(id)){
			    	for(Iterator suIt = item.elements("subItem").iterator();suIt.hasNext();){
			    		Element sbItem = (Element)suIt.next();
			    		String subId = sbItem.attribute("id").getText();
						String subHidden = sbItem.attribute("hidden").getText();
						
						if("401".equals(subId)){
							result.setMenu401(Boolean.valueOf(subHidden));
						}
						if("402".equals(subId)){
							result.setMenu402(Boolean.valueOf(subHidden));
						}
						if("403".equals(subId)){
							result.setMenu403(Boolean.valueOf(subHidden));
						}
						if("404".equals(subId)){
							result.setMenu404(Boolean.valueOf(subHidden));
						}
						if("405".equals(subId)){
							result.setMenu405(Boolean.valueOf(subHidden));
						}
						if("406".equals(subId)){
							result.setMenu406(Boolean.valueOf(subHidden));
						}
						if("407".equals(subId)){
							result.setMenu407(Boolean.valueOf(subHidden));
						}
			    	}
			    	
			    }
			    
			    if("5".equals(id)){
			    	for(Iterator suIt = item.elements("subItem").iterator();suIt.hasNext();){
			    		Element sbItem = (Element)suIt.next();
			    		String subId = sbItem.attribute("id").getText();
						String subHidden = sbItem.attributeValue("hidden");
						
						if("501".equals(subId)){
							result.setMenu501(Boolean.valueOf(subHidden));
						}
						if("502".equals(subId)){
							result.setMenu502(Boolean.valueOf(subHidden));
						}
						if("503".equals(subId)){
							result.setMenu503(Boolean.valueOf(subHidden));
						}
						if("504".equals(subId)){
							result.setMenu504(Boolean.valueOf(subHidden));
						}
						if("505".equals(subId)){
							result.setMenu505(Boolean.valueOf(subHidden));
						}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
}
