package com.wolf.app.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.app.data.entity.Sequence;
import com.wolf.app.web.service.SequenceService;
import com.wolf.core.exception.Errors;
import com.wolf.core.exception.ExceptionUtil;
import com.wolf.dao.base.Dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter
public class SequenceServicempl implements SequenceService {

	@Autowired
	private Dao<Sequence> dao;

	@Override
	public long nextval(String group, String name) {
		Sequence param = new Sequence();
		param.setCatagory(group);
		param.setName(name);
		param.setVal(1L);
		Long curval = dao.findOneBySqlMap("com.wolf.app.sql.curval", param);
		if (curval == null) {
			log.warn("sequence {}.{} not exist, try to create", group, name);
			dao.save(param);
			log.info("sequence {}.{} created, initial value is {}", group, name, param.getVal());
			curval = param.getVal();
		} else {
			log.debug("sequence {}.{} update from {} to {}", group, name, curval, curval + 1);
			int rows = dao.updateBySqlMap("com.wolf.app.sql.nextval", param);
			if (rows != 1) {
				ExceptionUtil.throwException(Errors.DB_ERROR, "update rows expect 1 actual " + rows);

			}
			curval++;
		}
		return curval;
	}

	@Override
	public String nextval(String group, String key, String template) {
		long nextval = this.nextval(group, key);
		String nextstr = String.valueOf(nextval);
		int idx = template.length() - nextstr.length();
		return template.substring(0, idx) + nextstr;
	}

}
