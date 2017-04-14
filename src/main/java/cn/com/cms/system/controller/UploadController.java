package cn.com.cms.system.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.cms.base.AppConfig;
import cn.com.cms.base.BaseController;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 文件上传控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/upload")
public class UploadController extends BaseController {
	@Resource
	private AppConfig appConfig;

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	@ResponseBody
	public void uploadFile(HttpServletRequest request, @RequestParam("baseId") String baseId,
			@RequestParam("uuid") String uuid, @RequestParam("dateTime") String dateTime) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (dateTime == null || "".equals(dateTime)) {
			dateTime = DateTimeUtil.getCurrentDateTimeString("yyyyMMdd");
		}
		// 附件上传路径
		String year = dateTime.substring(0, 4);
		String month = dateTime.substring(4, 6);
		String rootPath = appConfig.getAppPathHome();
		StringBuilder dirPath = new StringBuilder().append(rootPath).append("/doc/").append(baseId).append("/")
				.append(year).append("/").append(Integer.valueOf(month)).append("/").append(uuid).append("/");
		File directory = new File(dirPath.toString());
		if (!directory.exists())
			directory.mkdirs();
		// 处理上传文件
		MultipartFile multipartFile = multipartRequest.getFile("Filedata");
		if (multipartFile.getSize() != 0) {
			String originalName = multipartFile.getOriginalFilename();
			String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
			String fileName = System.currentTimeMillis() + "." + suffix;
			File file = new File(dirPath.toString() + fileName);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
