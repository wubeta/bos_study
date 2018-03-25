package cn.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.Servlet;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;


@Namespace("/")
@Scope("prototype")
@Controller
@ParentPackage("json-default")
public class CustomerAction extends BaseAction<Customer>{
	
	
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	@Action(value="customer_sendmessage")
	public String sendMessage(){
		/*String message = "0000";
		System.out.println(message);*/
		final String message = RandomStringUtils.randomAlphanumeric(4);
		System.out.println(message);
		
		jmsTemplate.send("bos",new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("message",message);
				mapMessage.setString("telephone",model.getTelephone());
				
				return mapMessage;
			}
		});
		
		
		
		/*发送短信省略*/
		
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), message);
		
		return NONE;
	}
	
	
	private String checkCode;
	
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Action(value="customer_regist",results={@Result(name="success",type="redirect",location="signup-success.html"),
			@Result(name="input",type="redirect",location="signup.html")})
	public String regist(){
		System.out.println("checkCode"+checkCode);
		if("0000".equals(checkCode)){
			System.out.println("success");
			WebClient.create("http://localhost:9902/crm_management/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);
			
			
			String activeCode = RandomStringUtils.random(32);
			
			redisTemplate.opsForValue().set(model.getTelephone(),activeCode, 24, TimeUnit.HOURS);
			
			String content = "<a href='http://localhost:9003/bos_fore/customer_activeMail&telephone="+model.getTelephone()+"&activeCode="+activeCode+"'>激活</a>";
			
			MailUtils.sendMail("激活邮件", content, model.getEmail());
			
			
			return SUCCESS;
		}
		System.out.println("error");
		return INPUT;
		
	}
	
	private String activeCode;
	
	
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@Action(value="customer_activeMail")
	public String activeMail() throws IOException{
		String code = redisTemplate.opsForValue().get(model.getTelephone());
		
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		if(activeCode==null||!activeCode.equals(code)){
			ServletActionContext.getResponse().getWriter().println("激活失败");
		}else{
			Customer customer = WebClient.create("http://localhost:9902/crm_management/services/customerService/customer/telephone/"+model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
			if(customer.getType()==null){
				WebClient.create("http://localhost:9902/crm_management/services/customerService/customer/updateType/"+model.getTelephone()).get();
			}
		}
		
		
		return NONE;
	}
}
