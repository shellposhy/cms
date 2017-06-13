package cn.com.cms.system.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.system.service.PathService;
import cn.com.cms.view.service.ViewPageService;

/**
 * 文件下载控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/download")
public class DownloadController extends BaseController {
	@Resource
	private AppConfig appConfig;
	@Resource
	private PathService pathService;
	@Resource
	private ViewPageService viewPageService;

	/**
	 * 基于页面模板，根据路径和文件名下载文件
	 * 
	 * @param pageId
	 * @param name
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/{name}")
	public void download(@PathVariable String name, HttpServletResponse response) throws FileNotFoundException {
		File file = new File(name);
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException();
		}
		String outputName = name;
		OutputStream out;
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();
			response.reset();
			response.addHeader("Content-Type", "text/html; charset=utf-8");
			response.addHeader("Content-Disposition","attachment;filename=" + new String(outputName.getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			out = new BufferedOutputStream(response.getOutputStream());
			out.write(buffer);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
