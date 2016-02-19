package cn.jiuling.vehicleinfosys2.service.impl;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	private Logger log = Logger.getLogger(MessageServiceImpl.class);

	@Resource
	private JmsTemplate myJmsTemplate;

	@Override
	public void sendMsg(final String msg) {
		myJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			};
		});

	}

}
