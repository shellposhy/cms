package cn.com.cms.util;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Strings;

/**
 * 关于文件操作工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class FileUtil extends FileUtils {

	/**
	 * 获得附件地址
	 * 
	 * @param rootPath
	 * @param createTime
	 * @param uuid
	 * @return
	 */
	public static String getDocFilePath(String rootPath, int baseId, String createTime, String uuid) {
		StringBuilder dirPath = new StringBuilder();
		String year = createTime.substring(0, 4);
		String month = createTime.substring(4, 6);
		dirPath.append(rootPath).append("/doc/").append(baseId).append("/");
		dirPath.append(year).append("/").append(Integer.valueOf(month)).append("/");
		dirPath.append(uuid).append("/");
		return dirPath.toString();
	}

	/**
	 * 格式化文件路径
	 * 
	 * @param filePath
	 */
	public static String formatFilePath(String filePath) {
		if (filePath == null)
			return filePath;
		return filePath.replace('\\', '/').replace("//", "/");
	}

	/**
	 * 根据文件路径获得文件名
	 * 
	 * @param filePath
	 */
	public static String getFileName(String filePath) {
		if (!Strings.isNullOrEmpty(filePath)) {
			filePath = filePath.replaceAll("\\\\", "\\/");
			return filePath.substring(filePath.lastIndexOf("/") + 1);
		}
		return null;
	}

	/**
	 * 根据文件路径获得文件后缀名
	 * 
	 * @param filePath
	 */
	public static String getFileSuffix(String filePath) {
		if (!Strings.isNullOrEmpty(filePath)) {
			filePath = filePath.replaceAll("\\\\", "\\/");
			return filePath.substring(filePath.lastIndexOf(".") + 1);
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 * @return
	 */
	public static boolean deleteFile(String filePathAndName) {
		boolean flag = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 * @return
	 */
	public static void deleteFolder(String folderPath) {
		try {
			deleteAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFile(path + "/" + tempList[i]);
				deleteFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获得文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp", ".mp4", ".flv", ".f4v", ".ogg", ".webm" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.endsWith(t)) {
				return t;
			}
		}
		return "";
	}

	/**
	 * 获得指定路径下的所有文件
	 * 
	 * @param realpath
	 * @param files
	 * @return
	 */
	public static List<File> getFiles(String realpath, List<File> files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					if (!getFileType(file.getName()).equals("")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	/**
	 * 获得文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return null;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				break;
			}
		}
		return temp;
	}
}
