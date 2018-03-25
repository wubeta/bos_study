package cn.itcast.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.PromotionRepository;
import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{

	@Autowired
	private PromotionRepository promotionRepository;
	
	
	@Override
	public void save(Promotion model) {
		promotionRepository.save(model);
	}


	@Override
	public Page<Promotion> pageQuery(Pageable pageable) {
		return promotionRepository.findAll(pageable);
	}


	@Override
	public PageBean<Promotion> pageQuery(int page, int rows) {
		Pageable pageable = new PageRequest(page, rows);
		
		Page<Promotion> pageDate = promotionRepository.findAll(pageable);
		
		PageBean<Promotion> pageBean = new PageBean<Promotion>();
		pageBean.setTotalCount(pageDate.getTotalElements());
		pageBean.setPageDate(pageDate.getContent());
		
		return pageBean;
	}


	@Override
	public Promotion findById(Integer id) {
		return promotionRepository.findOne(id);
	}

}
