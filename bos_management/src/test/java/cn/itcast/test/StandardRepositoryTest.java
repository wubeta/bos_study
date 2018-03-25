package cn.itcast.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Test
	public void test(){
//		List<Standard> list = standardRepository.findByName("aaaa");
//		List<Standard> list = standardRepository.queryName("aaaa");
//		List<FixedArea> list = fixedAreaRepository.findById("123");
//		fixedAreaRepository.fixedAreaDel("aa");
	}
	
	/*@Test
	@Transactional
	@Rollback(false)
	public void test2(){
		standardRepository.updateName(1, "bbbb");
	}*/
}
