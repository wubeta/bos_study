package cn.itcast.bos.mq;

import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import cn.itcast.bos.utils.SmsUtils;

@Service("SmsCustomer")
public class SmsCustomer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			SmsUtils.sendSmsByHTTP(mapMessage.getString("telephone"), mapMessage.getString("message"));
		} catch (UnsupportedEncodingException | JMSException e) {
			e.printStackTrace();
		}
	}

	
}
