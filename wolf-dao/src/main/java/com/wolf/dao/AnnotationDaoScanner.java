package com.wolf.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardClassMetadata;
import org.springframework.util.StringUtils;

import com.wolf.dao.annotation.DaoScan;
import com.wolf.dao.base.BaseDao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class AnnotationDaoScanner implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

	private ResourceLoader resourceLoader;
	private Environment environment;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(DaoScan.class.getName()));

		ClassPathDaoScanner scanner = new ClassPathDaoScanner(registry);
		scanner.setMapperProperties(environment);
		// this check is needed in Spring 3.1
		if (resourceLoader != null) {
			scanner.setResourceLoader(resourceLoader);
		}
		List<String> basePackages = new ArrayList<String>();
		for (String pkg : annoAttrs.getStringArray("value")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}
		if (basePackages.isEmpty()) {
			StandardClassMetadata scm = (StandardClassMetadata)importingClassMetadata;
			basePackages.add(scm.getIntrospectedClass().getPackage().getName());
		}
		for (String pkg : basePackages) {
			log.info("Dao scanner base package '{}'", pkg);
		}
		scanner.setMarkerInterface(BaseDao.class);
		scanner.registerFilters();
		scanner.doScan(StringUtils.toStringArray(basePackages));
	}

}
