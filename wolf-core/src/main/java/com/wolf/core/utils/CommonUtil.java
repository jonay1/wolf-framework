package com.wolf.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.wolf.core.base.ITree;
import com.wolf.core.exception.Errors;
import com.wolf.core.exception.ExceptionUtil;

public class CommonUtil {


	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static <T extends ITree<T>> List<T> convertTree(List<T> list) {
		Map<String, T> map = new HashMap<>();
		List<T> tree = new ArrayList<>();
		for (T iTree : list) {
			map.put(iTree.getId(), iTree);
		}
		for (T iTree : list) {
			T pNode = map.get(iTree.getPid());
			if (pNode == null) {
				tree.add(iTree);
			} else {
				pNode.getChildren().add(iTree);
			}
		}

		return tree;

	}

	public static String getExt(String fileName) {
		if (fileName == null) {
			return null;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf > -1) {
			return fileName.substring(lastIndexOf + 1);
		}
		return null;
	}

	public static String base64Img(InputStream imgIn) {
		byte[] data = null;
		try {
			data = new byte[imgIn.available()];
			imgIn.read(data);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
		return new String(Base64.encodeBase64(data), StandardCharsets.UTF_8);
	}

	public static boolean base64Img(String imgStr, OutputStream imgOut) {
		if (imgStr == null)
			return false;
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			imgOut.write(b);
			imgOut.flush();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static <T, R> List<R> foreach(Collection<T> collection, Function<T, R> mapper) {
		List<R> list = new ArrayList<R>();
		collection.forEach(i -> {
			list.add(mapper.apply(i));
		});
		return list;
	}

	public static boolean validate(Object bean, Class<?>... groups) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> set = validator.validate(bean, groups);
		if (!set.isEmpty()) {
			String result = set.stream().map(cv -> cv.getPropertyPath() + cv.getMessage())
					.collect(Collectors.joining(","));
			ExceptionUtil.throwException(Errors.OTHER, result);
		}
		return true;
	}

	public static String orgCode2SocCode(String orgCode) {
		return StringUtils.leftPad(StringUtils.replacePattern(orgCode, "(.{8})-(.)", "$1$20"), 18, "0");
	}

	public static String socCode2orgCode(String socCode) {
		return StringUtils.replacePattern(socCode, ".{8}(.{8})(.).", "$1-$2");
	}
	
	
	public static void main(String[] args) {
	}
}
