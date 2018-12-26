package com.wolf.app.web.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.core.service.MessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/i18n")
@Api(tags = "国际化")
public class MessageController {
	@Autowired
	private MessageService service;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@ApiOperation("查询所有国际化信息")
	@ApiResponses({ @ApiResponse(code = 200, message = "{键:值}", responseContainer = "Map") })
	public Map<String, String> list() {
		return service.getMessages();
	}

}
