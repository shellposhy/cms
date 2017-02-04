package cn.com.cms.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.com.cms.data.constant.EPicSuffixType;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片base64转换工具类
 * 
 * @author shishb
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class Base64ImageUtil {

	/**
	 * 把图片转化为base64字符串
	 * 
	 * @param imgRealPath
	 * @return
	 */
	public static String convertImage2Base64(String imgRealPath) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgRealPath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * 把base64图片字符串转化为新图片后保存
	 * 
	 * @param base64Str
	 * @param outputPath
	 * @param imgName
	 * @param suffix
	 * @return
	 */
	public static boolean convertBase642Image(String base64Str, String outputPath, String imgName,
			EPicSuffixType suffix) {
		if (null == base64Str || base64Str.length() < 1)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			outputPath = FileUtil.formatFilePath(outputPath);
			String filePath = "";
			if ("/".equals(outputPath.substring(outputPath.length() - 1))) {
				filePath = outputPath + imgName + suffix.getTitle();
			} else {
				filePath = outputPath + "/" + imgName + suffix.getTitle();
			}
			cn.com.people.data.util.FileUtil.createFolder(outputPath);
			OutputStream out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
