package com.wolf.dao.base;

import com.wolf.core.base.Pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageImpl implements Pagination {
	private int pageIndex = 0;
	private int pageSize = 10;

}
