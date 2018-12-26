package com.wolf.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.wolf.core.base.PageResult;
import com.wolf.core.base.Pagination;
import com.wolf.core.exception.Errors;
import com.wolf.core.exception.ExceptionUtil;
import com.wolf.core.utils.ClassUtil;
import com.wolf.core.utils.CommonUtil;
import com.wolf.core.utils.DateUtil;
import com.wolf.core.web.session.SessionUtil;
import com.wolf.dao.base.BaseDao;
import com.wolf.dao.base.BaseEntity;
import com.wolf.dao.base.Dao;
import com.wolf.dao.base.PageResultImpl;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Slf4j
public class DaoImpl<T> implements Dao<T> {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	private BaseDao<T> dao;
	private Class<?> entityClass;

	public DaoImpl() {
		entityClass = ClassUtil.getGenericClass(this.getClass());
	}

	private BaseDao<T> dao() {
		return dao;
	}

	@Override
	public List<T> findAll(String... order) {
		if (order == null || order.length == 0) {
			return dao().selectAll();
		} else {
			return this.findByExample(null, order);
		}
	}

	@Override
	public List<T> findByExample(Object record, String... order) {
		Example example = createExample(record, order);
		return dao().selectByExample(example);
	}

	@Override
	public PageResult<T> findByExample(Object record, Pagination page, String... order) {
		PageResultImpl<T> result = new PageResultImpl<>();
		List<T> list = null;
		Example example = createExample(record, order);
		long total = dao().selectCountByExample(example);
		result.setPageIndex(page.getPageIndex());
		result.setPageSize(page.getPageSize());
		RowBounds rowBounds = new RowBounds(page.getPageSize() * page.getPageIndex(), page.getPageSize());
		list = dao().selectByExampleAndRowBounds(example, rowBounds);
		result.setTotalCount(total);
		result.setRows(list);
		return result;
	}

	@Override
	public <E> List<E> findBySqlMap(String statement, Object parameter) {
		Assert.notNull(statement, "statement must not be null");
		log.debug("findBySqlMap:<{}>", statement);
		List<E> list = sqlSessionTemplate.selectList(statement, parameter);
		return list;
	}

	@Override
	public <E> List<E> findBySqlMap(String statement, Pagination page, Object parameter) {
		Assert.notNull(statement, "statement must not be null");
		log.debug("findBySqlMap:<{}>", statement);
		RowBounds rowBounds = new RowBounds(page.getPageSize() * page.getPageIndex(), page.getPageSize());
		List<E> list = sqlSessionTemplate.selectList(statement, parameter, rowBounds);
		return list;
	}

	@Override
	public <E> E findOneBySqlMap(String statement, Object parameter) {
		Assert.notNull(statement, "statement must not be null");
		log.debug("findOneBySqlMap:<{}>", statement);
		List<E> list = sqlSessionTemplate.selectList(statement, parameter);
		if (list.size() == 0) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			log.error("exist multi rows : {}", list.size());
			ExceptionUtil.throwException(Errors.DB_ERROR);
			return null;
		}
	}

	@Override
	public int deleteBySqlMap(String statement, Object parameter) {
		Assert.notNull(statement, "statement must not be null");
		log.debug("deleteBySqlMap:<{}>", statement);
		return sqlSessionTemplate.delete(statement, parameter);
	}

	@Override
	public int updateBySqlMap(String statement, Object parameter) {
		Assert.notNull(statement, "statement must not be null");
		log.debug("updateBySqlMap:<{}>", statement);
		return sqlSessionTemplate.update(statement, parameter);
	}

	@Override
	public boolean exist(Object example) {
		return !findByExample(example).isEmpty();
	}

	@Override
	public T findOne(Serializable id) {
		Assert.notNull(id, "id must not be null");
		return dao().selectByPrimaryKey(id);
	}

	@Override
	public T findOneByExample(T example) {
		List<T> list = findByExample(example);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			log.error("exist multi rows : {}", list.size());
			ExceptionUtil.throwException(Errors.DB_ERROR);
			return null;
		}
	}

	@Override
	public void save(T obj) {
		if (obj != null) {
			beforeSave(obj);
			dao().insert(obj);
		} else {
			log.warn("obj must be not null");
		}
	}

	@Override
	public void save(List<T> obj) {
		if (obj != null && !obj.isEmpty()) {
			obj.forEach(o -> {
				beforeSave(o);
			});
			dao().insertList(obj);
		} else {
			log.warn("No rows were affected");
		}
	}

	@Override
	public int update(T obj) {
		if (obj != null) {
			beforeUpdate(obj);
			int i = dao().updateByPrimaryKeySelective(obj);
			log.debug("{} rows were affected", i);
			return i;
		} else {
			log.warn("No rows were affected");
			return 0;
		}
	}

	@Override
	public int updateByExample(T record, Object t) {
		if (record != null) {
			beforeUpdate(record);
			Example example = createExample(t);
			int i = dao().updateByExampleSelective(record, example);
			log.debug("{} rows were affected", i);
			return i;
		} else {
			log.warn("No rows were affected");
			return 0;
		}
	}

	@Override
	public int delete(Serializable id) {
		int i = dao().deleteByPrimaryKey(id);
		log.debug("{} rows were affected, primary key '{}'", i, id);
		return i;
	}

	@Override
	public int deleteByExample(Object record) {
		Example example = createExample(record);
		int i = dao().deleteByExample(example);
		log.debug("delete rows {} by example {}", i, example);
		return i;
	}

	private Example createExample(Object record, String... order) {
		if (record != null && record instanceof Example) {
			return (Example) record;
		} else {
			Example example = new Example(entityClass, false, false);
			Criteria criteria = example.createCriteria();
			if (record != null) {
				criteria.andEqualTo(record);
			}
			if (order != null) {
				String orderBy = StringUtils.join(order, ",");
				example.setOrderByClause(orderBy);
			}
			return example;
		}
	}

	private void beforeSave(T obj) {
		if (obj instanceof BaseEntity) {
			BaseEntity entity = ((BaseEntity) obj);
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(CommonUtil.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(DateUtil.date19());
			}
			if (StringUtils.isBlank(entity.getCreator()) && SessionUtil.getSessionUser() != null) {
				entity.setCreator(SessionUtil.getSessionUser().getId());
			}
		}
	}

	private void beforeUpdate(T record) {
		if (record instanceof BaseEntity) {
			BaseEntity entity = ((BaseEntity) record);
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(DateUtil.date19());
			}
			if (StringUtils.isBlank(entity.getUpdator()) && SessionUtil.getSessionUser() != null) {
				entity.setUpdator(SessionUtil.getSessionUser().getId());
			}
		}
	}
}
