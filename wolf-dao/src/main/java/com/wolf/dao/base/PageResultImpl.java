package com.wolf.dao.base;

import java.util.ArrayList;
import java.util.List;

import com.wolf.core.base.PageResult;
import com.wolf.core.base.Pagination;

public class PageResultImpl<T> implements Pagination, PageResult<T> {
	private int pageIndex = 0;
	private int pageSize = 10;
	private long totalCount;
	private List<T> rows = new ArrayList<>();

	public PageResultImpl() {
	}

	public PageResultImpl(List<T> data) {
		rows = data;
	}

	public PageResultImpl(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public PageResultImpl<T> setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageResultImpl<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public List<T> getRows() {
		return rows;
	}

	public PageResultImpl<T> setRows(List<T> rows) {
		this.rows = rows;
		return this;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public PageResultImpl<T> setTotalCount(long total) {
		this.totalCount = total;
		return this;
	}

	public int getTotalPage() {
		return getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) getPageSize());
	}

}
