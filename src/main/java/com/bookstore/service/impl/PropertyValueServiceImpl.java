package com.bookstore.service.impl;

import com.bookstore.bean.PropertyValue;
import com.bookstore.dao.PropertyValueDao;
import com.bookstore.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Administrator on 2018/9/21.
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

	@Autowired
	PropertyValueDao propertyValueDao;

	@Override
	public int updateById(PropertyValue propertyvalue) {
		if (propertyvalue == null) return 0;
		return propertyValueDao.updateById(propertyvalue);
	}

	@Override
	public int deleteBatch(Long... id) {
		return propertyValueDao.deleteBatch(id);
	}

	@Override
	public List<PropertyValue> list() {
		return propertyValueDao.list();
	}

	@Override
	public int insert(PropertyValue propertyvalue) {
		return propertyValueDao.insert(propertyvalue);
	}
}
