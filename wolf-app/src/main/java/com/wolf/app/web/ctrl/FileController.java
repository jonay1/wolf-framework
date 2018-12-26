package com.wolf.app.web.ctrl;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wolf.app.consts.ParamKey;
import com.wolf.app.data.entity.FileInf;
import com.wolf.app.utils.SysParamUtil;
import com.wolf.core.utils.CommonUtil;
import com.wolf.core.utils.FileUtil;
import com.wolf.dao.base.Dao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "文件管理")
@Slf4j
@RequestMapping("/file")
public class FileController {

	@Autowired
	private Dao<FileInf> dao;

	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	@ApiOperation("单文件上传")
	public String[] upload(MultipartFile file, String bussType, String bussId) {
		return uploads(bussType, bussId, file);
	}

	@RequestMapping(path = "/uploads", method = RequestMethod.POST)
	@ApiOperation("多文件上传")
	public String[] uploads(String bussType, String bussId, MultipartFile... file) {
		String path = SysParamUtil.getString(ParamKey.SYS, ParamKey.UPLOAD_PATH, null);
		String[] ids = new String[] {};
		if (file != null) {
			ids = new String[file.length];
			int i = 0;
			for (MultipartFile mf : file) {
				FileInf fi = new FileInf();
				fi.setId(CommonUtil.uuid());
				fi.setFilename(mf.getOriginalFilename());
				fi.setFilesize(mf.getSize());
				fi.setRelId(bussId);
				fi.setRelType(bussType);
				fi.setContentType(mf.getContentType());
				String realPath = Paths.get(path, fi.getId()).toString();
				fi.setFilepath(realPath);
				ids[i++] = fi.getId();
				dao.save(fi);
				if (path != null) {
					FileUtil.transferTo(mf, path, fi.getId());
					log.info("upload file to {}", realPath);
				} else {
					log.warn("ParamKey.FILE_UPLOAD_PATH is undefined!");
				}
			}
		}
		return ids;
	}

	public void download() {

	}
}
