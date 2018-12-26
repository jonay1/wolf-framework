package com.wolf.core.base;

public interface TaskDetail {

	String getId();

	String getGroupName();

	String getName();

	String getBeanName();

	String getCron();
	
	boolean loggable();
}
