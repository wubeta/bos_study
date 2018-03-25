package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.take_delivery.impl.PromotionService;
import cn.itcast.bos.web.action.common.BaseAction;


@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("json-default")
public class PromotionAction extends BaseAction<Promotion>{

	private File titleImgFile;
	private String titleImgFileFileName;
	
	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	
	@Autowired
	private PromotionService promotionService;
	
	@Action(value="promotion_save",results={@Result(name="success",type="redirect",location="./pages/take_delivery/promotion.html")})
	public String promotionSave() throws IOException{
		System.out.println(titleImgFile);
		
		/*String url = ServletActionContext.getRequest().getContextPath()+"/upload/";
		String path= ServletActionContext.getServletContext().getRealPath("/upload");
		
		UUID uuid = UUID.randomUUID();
		String fileName = uuid+titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
		
		FileUtils.copyDirectory(titleImgFile, new File(path+"/"+fileName));
		
		
		model.setTitleImg(url+fileName);
		
		promotionService.save(model);*/
		
		return SUCCESS;
	}
	
	@Action(value="promotion_pagequery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		
		
		Pageable pageable = new PageRequest(page-1, rows);
		
		Page<Promotion> page = promotionService.pageQuery(pageable);
		
		System.out.println(page);
		
		pushValueStack(page);
		
		return SUCCESS;
	}
}
