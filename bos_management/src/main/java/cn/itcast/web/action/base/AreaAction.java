package cn.itcast.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;


@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

	private File file;
	public void setFile(File file) {
		this.file = file;
	}
	
	@Autowired
	private AreaService areaService;
	
	@Action(value="area_batchImport")
	public String batchImport() throws FileNotFoundException, IOException{
		List<Area> list = new ArrayList<Area>();
		System.out.println("------------");
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
		for (Row row : sheetAt) {
			if(row.getRowNum()==0){
				continue;
			}
			if(row.getCell(0)==null&&StringUtils.isBlank(row.getCell(0).getStringCellValue())){
				continue;
			}
			Area area = new Area();
			area.setId(row.getCell(0).getStringCellValue());
			area.setProvince(row.getCell(1).getStringCellValue());
			area.setCity(row.getCell(2).getStringCellValue());
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			list.add(area);
			
			String city = area.getCity().substring(0, area.getCity().length()-1);
			String province = area.getProvince().substring(0,area.getProvince().length()-1);
			String district = area.getDistrict().substring(0, area.getDistrict().length()-1);
			/*String[] str = PinYin4jUtils.getHeadByString(city+province+district);
			StringBuffer sb = new StringBuffer();
			for (String s : str) {
				sb.append(s);
			}
			area.setShortcode(sb.toString());
			
			area.setCitycode(PinYin4jUtils.hanziToPinyin(city, ""));*/
			
		}
		
		areaService.saveBatch(list);
		
		return NONE;
	}
	
	
	@Action(value="area_page",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		/*Specification<Area> specification = new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(StringUtils.isNotBlank(area.getProvince())){
					Predicate p = cb.like(root.get("province").as(String.class),"%"+area.getProvince()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(area.getDistrict())){
					Predicate p = cb.like(root.get("district").as(String.class),"%"+area.getDistrict()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(area.getCity())){
					Predicate p = cb.like(root.get("city").as(String.class),"%"+area.getCity()+"%");
					list.add(p);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};*/
		
		Specification<Area> specification = new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getProvince())){
					Predicate p = cb.like(root.get("province").as(String.class),"%"+model.getProvince()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(model.getCity())){
					Predicate p = cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(model.getDistrict())){
					Predicate p = cb.like(root.get("district").as(String.class),"%"+model.getDistrict()+"%");
					list.add(p);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		Page<Area> page = areaService.findAll(pageable,specification);
		pushValueStack(page);
		return SUCCESS;
	}
	
}
