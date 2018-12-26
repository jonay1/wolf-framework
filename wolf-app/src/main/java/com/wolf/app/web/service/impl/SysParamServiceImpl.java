package com.wolf.app.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.app.data.entity.SysParam;
import com.wolf.app.web.service.SysParamService;
import com.wolf.dao.base.Dao;

@Service
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private Dao<SysParam> dao;

	public SysParam findOne(String type, String key) {
		SysParam example = new SysParam();
		example.setParamType(type);
		example.setParamKey(key);
		return dao.findOneByExample(example);
	}

	@Override
	public String param(String type, String key) {
		SysParam param = findOne(type, key);
		String value = null;
		if (param != null) {
			value = param.getParamVal();
		}
		return value;
	}

	@Override
	public List<SysParam> findAll() {
		return dao.findAll();
	}

	@Override
	public void insertParam(String type, String key, String value) {
		SysParam param = new SysParam();
		param.setParamType(type);
		param.setParamKey(key);
		param.setParamVal(value);
		param.setStatus(STATUS_VALID);
		dao.save(param);
	}

	@Override
	public void updateParam(String type, String key, String value) {
		SysParam param = findOne(type, key);
		if (param != null) {
			param.setParamVal(value);
			dao.update(param);
		}
	}

	@Override
	public void disableParam(String type, String key) {
		SysParam param = findOne(type, key);
		if (param != null) {
			param.setStatus(STATUS_VALID);
			dao.update(param);
		}
	}

	@Override
	public void enableParam(String type, String key) {
		SysParam param = findOne(type, key);
		if (param != null) {
			param.setStatus(STATUS_INVALID);
			dao.update(param);
		}
	}

}
