package cn.itcast.web.action.base;

import java.util.HashMap;
import java.util.Map;

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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

	private Courier courier = new Courier();
	
	@Autowired
	private CourierService courierService;
	
	@Override
	public Courier getModel() {
		return courier;
	}

	@Action(value="courrier_save",results={@Result(name="success",type="redirect",location="./pages/base/courier.html")})
	public String save(){
		courierService.save(courier);
		return SUCCESS;
	}
	
	
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	@Action(value="courier_page",results={@Result(name="success",type="json")})
	public String findPageDate(){
		Pageable pageable= new PageRequest(page-1, rows);
		Page<Courier> page = courierService.findAll(pageable);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",page.getTotalElements());
		map.put("rows",page);
		
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	
	
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="courier_del",results={@Result(name="success",type="redirect",location="./pages/base/courier.html")})
	public String updateDeltag(){
		String[] idArray = ids.split(",");
		courierService.updateDeltag(idArray);
		return SUCCESS;
	}
}
