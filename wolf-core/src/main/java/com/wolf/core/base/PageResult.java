package com.wolf.core.base;

import java.util.List;

public interface PageResult<T> extends IResult {

	List<T> getRows();

	long getTotalCount();

	int getTotalPage();
}
