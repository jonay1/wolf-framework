package com.wolf.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.RandomUtils;

import com.wolf.core.exception.ExceptionUtil;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;

public class ClassUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T newInstance(Class<T> beanClass) {
		try {
			return beanClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * 获取类的泛型类型
	 * 
	 * @param target
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> target) {
		if (target.isInterface()) {
			return getGenericInterface(target);
		} else {

			Type genType = target.getGenericSuperclass();
			if (genType != null && genType instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
				return (Class<?>) params[0];
			}
			Type[] genTypes = target.getGenericInterfaces();
			if (genTypes != null && genTypes.length > 0 && genTypes[0] instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) genTypes[0]).getActualTypeArguments();
				return (Class<?>) params[0];
			}

		}
		return null;
	}

	/**
	 * 获取接口的泛型类型
	 * 
	 * @param target
	 * @return
	 */
	private static Class<?> getGenericInterface(Class<?> target) {
		if (target.isInterface()) {
			ClassPool classPool = ClassPool.getDefault();
			classPool.appendClassPath(new ClassClassPath(ClassUtil.class));
			try {
				CtClass clazz = classPool.get(target.getName());
				// Ljava/lang/Object;Lorg/wolf/component/mybatis/base/BaseMapper<Lorg/wolf/console/entity/Sequence;>;
				String genericSignature = clazz.getGenericSignature();
				if (genericSignature != null) {
					String[] split = genericSignature.split("[<;>]");
					String className = split[split.length - 1].substring(1).replaceAll("/", ".");
					return Class.forName(className);
				}
			} catch (Exception e) {
			}
		}
		return null;

	}

	public static Class<?> genGenericInstance(Class<?> interfaceCls, Class<?> entityCls) {
		Class<?> clazz = null;
		ClassPool pool = ClassPool.getDefault();
		String newClassName = entityCls + "$" + RandomUtils.nextInt();
		CtClass subClass = pool.makeClass(newClassName);
		CtClass[] params = new CtClass[] {};
		try {
			CtConstructor ctor = CtNewConstructor.make(params, null, CtNewConstructor.PASS_PARAMS, null, null,
					subClass);
			subClass.addConstructor(ctor);

			subClass.setSuperclass(pool.get(interfaceCls.getName()));
			// "Lcom/wolf/app/module/mybatis/DaoImpl<Ljava/lang/String;>;";
			String jvmTypeName = "L" + interfaceCls.getName().replaceAll("\\.", "/") + "<L"
					+ entityCls.getName().replaceAll("\\.", "/") + ";>;";
			subClass.setGenericSignature(jvmTypeName);
			// subClass.writeFile("target/classes");
			clazz = subClass.toClass();
			// clazz = Class.forName(newClassName, false, pool.getClassLoader());
		} catch (Exception e) {
			ExceptionUtil.throwException(e);
		}
		return clazz;
	}
}
