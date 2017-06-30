package cn.com.cms.page.controller.base;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.page.constant.EIndustryType;
import cn.com.cms.page.model.WebUser;
import cn.com.cms.page.service.WebUserService;
import cn.com.people.data.util.StringUtil;

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
	public void register(HttpServletRequest request, HttpServletResponse response, Model model) {
		WebUser user = new WebUser();
		user.setName(request.getParameter("name"));
		user.setPass(StringUtil.encodeToMD5(request.getParameter("pass")));
		user.setCompany(request.getParameter("danwei"));
		int industry = !Strings.isNullOrEmpty(request.getParameter("industry"))
				? Integer.valueOf(request.getParameter("industry")) : EIndustryType.Other.ordinal();
		user.setIndustry(EIndustryType.valuesOf(industry));
		user.setRealName(request.getParameter("realname"));
		user.setPosition(request.getParameter("position"));
		user.setTelphone(request.getParameter("telphone"));
		user.setMobile(request.getParameter("cellphone"));
		user.setEmail(request.getParameter("email"));
		user.setAddress(request.getParameter("address"));
		user.setPostCode(request.getParameter("postcode"));
		user.setFax(request.getParameter("fax"));
		user.setCreateTime(new Date());
		webUserService.saveOrUpdate(user);
		try {
			response.sendRedirect("/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
