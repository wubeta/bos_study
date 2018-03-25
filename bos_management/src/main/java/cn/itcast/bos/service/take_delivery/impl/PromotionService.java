package cn.itcast.bos.service.take_delivery.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;

@Service
@Transactional
public interface PromotionService {

	void save(Promotion model);
	

	Page<Promotion> pageQuery(Pageable pageable);

	@Path("/pageQuery")
	@GET
	@Produces({"application/json","application/xml"})
	PageBean<Promotion> pageQuery(@QueryParam("page") int page,@QueryParam("rows") int rows);
	
	@Path("/promotion/{id}")
	@GET
	@Produces({"application/json","application/xml"})
	Promotion findById(@PathParam("id") Integer id);
	
}
