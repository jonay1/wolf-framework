package com.wolf.app.comm.bean;

import com.wolf.core.base.IResult;

import lombok.Data;

@Data
public class Result<T> implements IResult {
	private int code;
	private String message;
	private T data;
}
