package cn.jiuling.vehicleinfosys2.listener;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.service.ServerService;

@Component
public class ConsumerMessageListener implements MessageListener {
	private static Logger log = Logger.getLogger(ConsumerMessageListener.class);
	@Resource
	private ServerService serverService;

	@Override
	public void onMessage(Message message) {
		TextMessage textMsg = (TextMessage) message;
		try {
			String text = textMsg.getText();
			log.info("消息内容是：" + text);
			ObjectMapper mapper = new ObjectMapper();
			Serverinfo s = mapper.readValue(text, Serverinfo.class);
			serverService.saveOrUpdate(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}