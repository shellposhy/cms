package cn.com.cms.library.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.system.service.ImagePathService;
import cn.com.cms.data.util.DataUtil;

/**
 * 图片处理服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class PictureService {
	@Resource
	private ImagePathService imagePathService;

	/**
	 * 替换文章中图片路径
	 * 
	 * @param content
	 * @param files
	 * @return
	 */
	public String replaceContentPic(String content, List<File> files, int baseId, String createTime) {
		try {
			List<String> imgUrls = DataUtil.getImgs(content);
			if (imgUrls != null && imgUrls.size() > 0) {
				String realPath = imagePathService.getUPicRelRoot(baseId, createTime);
				for (String str : imgUrls) {
					String fileName = str.substring(str.lastIndexOf("/") + 1);
					for (File file : files) {
						if (fileName.equals(file.getName())) {
							//content = content.replaceAll(str, realPath + fileName);
							content = content.replaceAll(str, "/pic/"+realPath + fileName);
						}
					}
				}
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return content;
		}
	}
}
