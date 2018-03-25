package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.constant.Constant;
import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.web.action.common.BaseAction;
import freemarker.template.Configuration;
import freemarker.template.Template;


@Namespace("/")
@Scope("prototype")
@Controller
@ParentPackage("json-default")
public class PromotionAction extends BaseAction<Promotion>{
	
	
	@Action(value="promotion_pagequery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		PageBean<Promotion> pageBean = WebClient.create(Constant.BOS_MANAGEMENT_URL+"/bos_management/services/promotionService/pageQuery").accept(MediaType.APPLICATION_JSON).get(PageBean.class);
		
		ActionContext.getContext().getValueStack().push(pageBean);
		return SUCCESS;
	}
	
	@Action(value="promotion_showdetail")
	public String showDetail() throws Exception{
		String path  = ServletActionContext.getServletContext().getRealPath("/freemark");
		
		File file = new File(path+"/"+model.getId()+".html");
		
		if(!file.exists()){
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
			configuration.setDirectoryForTemplateLoading(new File(ServletActionContext.getServletContext().getRealPath("/WEB-INF/templates")));
			
			Template template = configuration.getTemplate("promotion_detail.ftl");
			Promotion promotion = WebClient.create(Constant.BOS_MANAGEMENT_URL+"/bos_management/services/promotionService/promotion/"+model.getId()).accept(MediaType.APPLICATION_JSON).get(Promotion.class);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("promotion", promotion);
		    template.process(map,new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));

		}
		
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		FileUtils.copyFile(file, ServletActionContext.getResponse().getOutputStream());
		
		return NONE;
	}
}
