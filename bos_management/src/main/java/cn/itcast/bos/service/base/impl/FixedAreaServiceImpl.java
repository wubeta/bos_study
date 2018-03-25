package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Override
	public void FixedAreaSave(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> fixedAreaPageData(Pageable pageable, Specification<FixedArea> specification) {
		return fixedAreaRepository.findAll(specification, pageable);
	}

	@Override
	public void fixedAreaDel(String ids) {
		String[] idsArr = ids.split(",");
		for (String id : idsArr) {
			FixedArea fixedArea = fixedAreaRepository.findById(id).get(0);
			fixedAreaRepository.delete(fixedArea);
//			System.out.println(fixedArea);
		}
	}

}
