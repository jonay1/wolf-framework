package com.wolf.app.comm.handler;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolf.app.comm.bean.Result;
import com.wolf.core.exception.AppException;
import com.wolf.core.exception.Errors;
import com.wolf.core.exception.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();
		String errmsg = fieldError.getDefaultMessage();
		return buildValidError(errmsg);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseBody
	public Result<?> handleException(Exception ex) {
		log.error("exception occured : ", ex);
		Result<?> r = new Result<>();
		AppException e = ExceptionUtil.wrap(ex);
		int code = e.getCode();
		String message = null;
		if (ex instanceof BindException) {
			BindingResult result = ((BindException) ex).getBindingResult();
			FieldError error = result.getFieldError();
			message = error.getDefaultMessage();
		} else if (ex instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) ex).getConstraintViolations();
			for (ConstraintViolation<?> violation : errors) {
				message = violation.getMessage();
				break;
			}
		} else {
			code = e.getCode();
			message = e.getMessage();
		}

		r.setCode(code);
		r.setMessage(message);
		return r;
	}

	private Result<?> buildValidError(String message) {
		Result<?> r = new Result<>();
		r.setCode(Errors.ILLEGAL_DATA.code());
		r.setMessage(message);
		return r;
	}
}
