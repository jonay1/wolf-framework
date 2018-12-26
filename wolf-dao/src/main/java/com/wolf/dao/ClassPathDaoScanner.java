package com.wolf.dao;

import java.util.Set;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;

import com.wolf.core.utils.ClassUtil;
import com.wolf.dao.base.BaseDao;
import com.wolf.dao.impl.DaoImpl;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.spring.mapper.MapperFactoryBean;

@Slf4j
public class ClassPathDaoScanner extends org.mybatis.spring.mapper.ClassPathMapperScanner {

	private MapperHelper mapperHelper = new MapperHelper();

	public ClassPathDaoScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		doAfterScan(beanDefinitions);
		return beanDefinitions;
	}

	protected void doAfterScan(Set<BeanDefinitionHolder> beanDefinitions) {
		// 如果没有注册过接口，就注册默认的Mapper接口
		this.mapperHelper.ifEmptyRegisterDefaultInterface();
		GenericBeanDefinition definition;
		for (BeanDefinitionHolder holder : beanDefinitions) {
			definition = (GenericBeanDefinition) holder.getBeanDefinition();
			if (StringUtil.isNotEmpty(definition.getBeanClassName()) && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
				definition.setBeanClass(MapperFactoryBean.class);
				definition.getPropertyValues().add("mapperHelper", this.mapperHelper);

				ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) definition;
				Class<?> entityClass = null;
				try {
					Class<?> realClassName = Class.forName(sbd.getMetadata().getClassName());
					entityClass = ClassUtil.getGenericClass(realClassName);
				} catch (ClassNotFoundException e) {
					throw new CannotLoadBeanClassException("MapperFactoryBean", holder.getBeanName(), sbd.getMetadata().getClassName(), e);
				}
				newBean(entityClass, getRegistry());
			}
		}
	}

	/**
	 * 从环境变量中获取 mapper 配置信息
	 *
	 * @param environment
	 */
	public void setMapperProperties(Environment environment) {

		Binder binder = Binder.get(environment);
		BindResult<Config> bind = binder.bind(Config.PREFIX, Config.class);
		if (bind.isBound()) {
			mapperHelper.setConfig(bind.get());
		}
		mapperHelper.registerMapper(BaseDao.class);

	}

	private String newBean(Class<?> entityClass, BeanDefinitionRegistry registry) {
		String beanKey = daoImplName(entityClass);
		if (!registry.containsBeanDefinition(beanKey)) {
			Class<?> daoProxy = ClassUtil.genGenericInstance(DaoImpl.class, entityClass);
			BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(daoProxy);
			bdb.getBeanDefinition().setAttribute("id", beanKey);
			bdb.setLazyInit(true);
			AbstractBeanDefinition rawBeanDefinition = bdb.getRawBeanDefinition();
			registry.registerBeanDefinition(beanKey, rawBeanDefinition);
			log.info("auto register bean:{} [{}<{}>]", beanKey, DaoImpl.class.getName(), entityClass.getSimpleName());
		} else {
			// ignore
		}
		return beanKey;
	}

	private String daoImplName(Class<?> entityClass) {
		return entityClass.getSimpleName() + "DaoImplProxy";
	}
}
