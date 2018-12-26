package com.wolf.app.web.service;

import java.util.List;

import com.wolf.app.data.entity.SysParam;

public interface SysParamService {
	String STATUS_VALID = "1";
	String STATUS_INVALID = "0";

	String param(String type, String key);

	List<SysParam> findAll();
	
	void insertParam(String type, String key, String value);

	void updateParam(String type, String key, String value);

	void disableParam(String type, String key);

	void enableParam(String type, String key);
}
