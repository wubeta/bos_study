package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	public List<Customer> findByFixedAreaIdIsNull();

	public List<Customer> findByFixedAreaId(String id);

	@Query("update Customer set fixedAreaId=?2 where id=?1")
	@Modifying
	public void updateFixedAreaId(int parseInt, String fixedAreaId);

	public Customer findByTelephone(String telephone);

	
	@Query("update Customer set type=1 where telephone=?")
	@Modifying
	public void updateType(String telephone);

}
