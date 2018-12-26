package com.wolf.core.base;

import java.util.List;

public interface ITree<T> {

	String getId();

	String getPid();

	List<T> getChildren();
}
