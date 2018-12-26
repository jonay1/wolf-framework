package com.wolf.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.wolf.core.exception.ExceptionUtil;

public class FileUtil {

	public static void transferTo(MultipartFile file, String path, String filename) {
		File dest = new File(path, filename);
		try {
			if (!dest.exists()) {
				dest.mkdirs();
			}
			file.transferTo(dest);
		} catch (IOException e) {
			ExceptionUtil.throwException(e);
		}
	}

	public static InputStream readFile(String classpath) {
		return FileUtil.class.getClassLoader().getResourceAsStream(classpath);
	}
}
