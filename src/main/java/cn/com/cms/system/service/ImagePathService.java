package cn.com.cms.system.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.com.cms.base.config.AppConfig;
import cn.com.cms.util.FileUtil;

/**
 * 图片路径服务
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ImagePathService {
	@Resource
	private AppConfig appConfig;

	// 用户库图片库
	private static final String UPIC = "upic";
	private static final String MPIC = "mpic";

	/**
	 * 获得系统文件临时文件根目录
	 * 
	 * @return
	 */
	public String getTempPath() {
		return getRoot() + "/tmp/";
	}

	/**
	 * 获得系统文件系统根目录
	 * 
	 * @return
	 */
	public String getRoot() {
		return appConfig.getAppPathHome();
	}

	/**
	 * 获得系统文件水印根目录
	 * 
	 * @return
	 */
	public String getMarkRoot() {
		return getRoot() + "/pic/mark/";
	}

	/**
	 * 获得系统文件相册首页根目录
	 * 
	 * @return
	 */
	public String getLogoRoot() {
		return getRoot() + "/pic/logo/";
	}

	/**
	 * 获得系统文件系统用户图片根目录
	 * 
	 * @return
	 */
	public String getUPicRoot() {
		return getRoot() + "/pic/upic/";
	}

	/**
	 * 获得系统文件系统手机图片真实根目录
	 * 
	 * @param baseId
	 * @param createTime
	 * @return
	 */
	public String getUPicRealRoot(int baseId, String createTime) {
		return getUPicRoot() + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6)) + "/";
	}

	/**
	 * 获得系统文件系统手机图片相对根目录
	 * 
	 * @param baseId
	 * @param createTime
	 * @return
	 */
	public String getUPicRelRoot(int baseId, String createTime) {
		return UPIC + "/" + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6)) + "/";
	}

	/**
	 * 获得系统文件系统系统图片根目录
	 * 
	 * @return
	 */
	public String getPicRoot() {
		return getRoot() + "/pic/pic/";
	}

	/**
	 * 获得系统文件系统视频文件根目录
	 * 
	 * @return
	 */
	public String getVideoRoot() {
		return getRoot() + "/video/";
	}

	/**
	 * 获得系统文件系统附件根目录
	 * 
	 * @return
	 */
	public String getDocRoot() {
		return getRoot() + "/doc/";
	}

	/**
	 * 获得系统文件系统手机图片根目录
	 * 
	 * @return
	 */
	public String getMPicRoot() {
		return getRoot() + "/pic/mpic/";
	}

	/**
	 * 获得系统文件系统手机图片真实根目录
	 * 
	 * @param baseId
	 * @param createTime
	 * @return
	 */
	public String getMPicRealRoot(int baseId, String createTime) {
		return getMPicRoot() + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6)) + "/";
	}

	/**
	 * 获得系统文件系统手机图片真实根目录
	 * 
	 * @param baseId
	 * @param createTime
	 * @return
	 */
	public String getMPicRelRoot(int baseId, String createTime) {
		return MPIC + "/" + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6)) + "/";
	}

	/**
	 * 根据文件名， 获得上传的临时文件路径，
	 * <p>
	 * 如果存在返回路径
	 * <p>
	 * 否则返回<code>Null</code>
	 * 
	 * @param directory
	 * @param fileName
	 */
	public File getFilePath(String directory, String fileName) {
		List<File> files = FileUtil.getFiles(directory, new ArrayList<File>());
		for (File file : files) {
			String name = file.getName();
			if (fileName.equals(name.substring(0, name.lastIndexOf(".")))) {
				return file;
			}
		}
		return null;
	}

	/**
	 * 根据图片相对路径获得图片的绝对路径
	 * 
	 * @param relPath
	 * @return
	 */
	public String getPicAbsolutePath(String relPath) {
		relPath = relPath.replaceAll("\\\\", "\\/");
		if (!Strings.isNullOrEmpty(relPath) && relPath.substring(0, 1).equals("/")) {
			return getRoot() + relPath;
		} else {
			return getRoot() + "/pic/" + relPath;
		}
	}
}
