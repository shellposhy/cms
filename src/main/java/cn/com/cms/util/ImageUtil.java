package cn.com.cms.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import cn.com.cms.data.constant.EPicSuffixType;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片base64转换工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class ImageUtil {
	private static Logger log = Logger.getLogger(ImageUtil.class.getName());

	/**
	 * 产生图片二维码
	 * 
	 * @param code
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static void productQRCode(String code, String name, int width, int height) {
		Map<EncodeHintType, Object> hints = Maps.newHashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			log.debug("=====create bar code=====");
			BitMatrix matrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
			Path path = FileSystems.getDefault().getPath(MessageResources.getValue("app.path.code"), name + ".png");
			try {
				MatrixToImageWriter.writeToPath(matrix, "png", path);
			} catch (IOException e) {
				log.info("===write qr code file error===");
				e.printStackTrace();
			}
		} catch (WriterException e) {
			log.info("===create qr code error===");
			e.printStackTrace();
		}
	}

	/**
	 * 产生图片条形码
	 * 
	 * @param code
	 * @param name
	 * @param dpi
	 * @return
	 */
	public static void productBarcode(String code, String name, int dpi) throws Exception {
		log.debug("=====create bar code=====");
		if (dpi < 1)
			dpi = 100;
		Code128Bean code128Bean = new Code128Bean();
		code128Bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
		code128Bean.doQuietZone(false);
		File filePath = new File(MessageResources.getValue("app.path.code"));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File outputFile = new File(MessageResources.getValue("app.path.code") + "/" + name + ".png");
		OutputStream out = new FileOutputStream(outputFile);
		BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", dpi,
				BufferedImage.TYPE_BYTE_BINARY, false, 0);
		code128Bean.generateBarcode(provider, code);
		provider.finish();
		out.close();
	}

	/**
	 * 把图片转化为base64字符串
	 * 
	 * @param imgRealPath
	 * @return
	 */
	public static String convertImage2Base64(String imgRealPath) {
		log.debug("=====create base64 code=====");
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
		log.debug("=====base64 code to image=====");
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
