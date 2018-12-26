package com.wolf.dao.base;

import java.io.Serializable;
import java.util.List;

import com.wolf.core.base.PageResult;
import com.wolf.core.base.Pagination;

public interface Dao<T> {

	List<T> findAll(String... order);

	/**
	 * 通过自定义语句进行查询，语句编写在xxxMapper.xml中，返回对象列表
	 * 
	 * @param statement
	 *            命名空间+语句id
	 * @param parameter
	 *            传入参数
	 * @return 返回对象集合
	 */
	<E> List<E> findBySqlMap(String statement, Object parameter);

	<E> List<E> findBySqlMap(String statement, Pagination page, Object parameter);

	<E> E findOneBySqlMap(String statement, Object parameter);

	int deleteBySqlMap(String statement, Object parameter);

	int updateBySqlMap(String statement, Object parameter);

	boolean exist(Object example);

	T findOne(Serializable id);

	T findOneByExample(T example);

	List<T> findByExample(Object example, String... order);

	PageResult<T> findByExample(Object example, Pagination page, String... order);

	void save(T obj);

	void save(List<T> obj);

	int update(T obj);

	int updateByExample(T obj, Object example);

	int delete(Serializable id);

	int deleteByExample(Object example);

}