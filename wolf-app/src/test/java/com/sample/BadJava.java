package com.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 糟糕的代码
 * 
 * @author jonay
 *
 */
public class BadJava implements Serializable {
	// 日志logger
	private static Logger log = LogManager.getLogger(BadJava.class);

	// 是否需要初始化
	private static final boolean shouldInit = true;

	public static void init() {

	}

	public String say(String name) {
		if (name == null || name.equals("")) {
			return "";
		} else {
			return "hello " + name;
		}
	}

	public static void main(String[] args) {
		try {
			log.info("begin, args length = " + args.length);
			BadJava BadJava = new BadJava();
			if (shouldInit)
				BadJava.init();

			String name = args[0];
			String notused = null;

			List list1 = new ArrayList();
			list1.add("hack");
			if (list1 != null)
				if (!list1.contains(name))
					BadJava.say(name);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error:" + e.getMessage());
		}

	}
}
