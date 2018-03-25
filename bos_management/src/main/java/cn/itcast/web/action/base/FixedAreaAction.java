package cn.itcast.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;


@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("json-default")
public class FixedAreaAction extends BaseAction<FixedArea>{
	
	@Autowired
	private FixedAreaService fixedAreaService;
	
	
	@Action(value="fixedarea_save",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String FixedAreaSave(){
//		System.out.println(model);
		fixedAreaService.FixedAreaSave(model);
		return SUCCESS;
	}
	
	
	
	@Action(value="fixedarea_page",results={@Result(name="success",type="json")})
	public String fixedAreaPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		
		Specification<FixedArea> specification = new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				if (StringUtils.isNotBlank(model.getId())) {
					Predicate p = cb.like(root.get("id").as(String.class), "%"+model.getId()+"%");
					list.add(p);
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(p);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		Page<FixedArea> page = fixedAreaService.fixedAreaPageData(pageable,specification);
		
		pushValueStack(page);
		
//		System.out.println(page.getContent());
		
		return SUCCESS;
	}
	
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="fixedarea_del",results={@Result(name="success",location="./pages/base/fixed_area.html",type="redirect")})
	public String fixedAreaDel(){
//		System.out.println(ids);
		fixedAreaService.fixedAreaDel(ids);
		return SUCCESS;
	}
	
	@Action(value="fixedarea_findNoAssociationCustomers",results={@Result(name="SUCCESS",type="json")})
	public String findNoAssociationCustomers(){
		Collection<? extends Customer> collection = WebClient.create("http://localhost:9902/crm_management/services/customerService/noassociationcustomers").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	
	
	@Action(value="fixedarea_findHasAssociationCustomers",results={@Result(name="SUCCESS",type="json")})
	public String findHasAssociationCustomers(){
		Collection<? extends Customer> collection = WebClient.create("http://localhost:9902/crm_management/services/customerService/associationcustomers/"+model.getId()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
}
