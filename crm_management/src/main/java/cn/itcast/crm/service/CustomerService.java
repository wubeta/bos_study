package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;

public interface CustomerService {
	
	@Path("/noassociationcustomers")
	@GET
	@Produces({"application/xml","application/json"})
	public List<Customer> findNoAssociationCustomers();
	
	
	@Path("/associationcustomers/{fixedAreaId}")
	@GET
	@Produces({"application/xml","application/json"})
	public List<Customer> findHasAssociationCustomers(@PathParam("fixedAreaId") String fixedAreaId);
	
	@Path("/associatincustomertofixedarea")
	@PUT
	public void associationCustomerToFixedArea(@QueryParam("fixedAreaIds") String fixedAreaIds,@QueryParam("customerId") String customerId);
	
	
	@Path("/customer")
	@POST
	@Consumes({"application/xml","application/json"})
	public void regist(Customer customer);
	
	@Path("/customer/telephone/{telephone}")
	@GET
	@Consumes({"application/xml","application/json"})
	public Customer findByTelephone(@PathParam("telephone") String telephone);
	
	@Path("/customer/updateType/{telephone}")
	@GET
	public void updateType(@PathParam("telephone") String telephone);
}
