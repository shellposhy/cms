package cn.com.cms.page.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cms.base.BaseController;
import cn.com.cms.page.service.WebUserService;

/**
 * 客户控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/page/user")
public class ClientController extends BaseController {

	@Resource
	private WebUserService webUserService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, Model model) {

		return null;
	}
}
