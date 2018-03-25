package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void FixedAreaSave(FixedArea model);

	Page<FixedArea> fixedAreaPageData(Pageable pageable, Specification<FixedArea> specification);

	void fixedAreaDel(String ids);

}
