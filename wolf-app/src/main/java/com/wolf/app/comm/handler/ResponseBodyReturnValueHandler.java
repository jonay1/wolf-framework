package com.wolf.app.comm.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wolf.app.comm.bean.Result;
import com.wolf.core.base.IResult;

public class ResponseBodyReturnValueHandler implements HandlerMethodReturnValueHandler {
	private final HandlerMethodReturnValueHandler delegate;

	public ResponseBodyReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		Object result = returnValue;
		if (result != null) {
			if (!(result instanceof IResult)) {
				Result<Object> r = new Result<Object>();
				r.setData(returnValue);
				result = r;
			}
		} else {
			Result<Object> r = new Result<Object>();
			result = r;
		}
		delegate.handleReturnValue(result, returnType, mavContainer, webRequest);
	}
}