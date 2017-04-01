package cn.com.cms.view.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.cms.base.BaseController;
import cn.com.cms.view.service.ViewListService;

/**
 * 列表页/频道/专题控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/view/page/channel")
public class ViewListController extends BaseController {

	@Resource
	private ViewListService viewListService;

	/**
	 * 列表页/频道/专题页面发布
	 */
	@RequestMapping("/publish")
	public void publish() {
		viewListService.publish();
	}
}
