package com.wolf.core.exception;

import javax.validation.constraints.NotNull;

public enum Errors {

	SUCCESS("操作成功"),

	ERROR("操作失败"),

	SESSION_USER_INVALID("会话无效"),

	FORBIDDEN("拒绝访问"), ILLEGAL_DATA("非法数据"), NOT_FOUND("资源不存在"),

	DB_ERROR("数据库异常"),

	OTHER("{0}");

	private String message;

	private Errors(@NotNull String message) {
		this.message = message;
	}

	public int code() {
		return this.ordinal();
	}

	public String message() {
		return this.message;
	}

	public String toString() {
		return message();
	}

}
