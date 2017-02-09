package cn.com.cms.export.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PRTokeniser;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import cn.com.cms.util.MessageResources;
import cn.com.people.data.util.FileUtil;

/**
 * 导出pdf工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class ExportPdfFileUtil {
	private String fontRootPath = MessageResources.getValue("app.path.font"); // 字体文件路径
	private final String SIMSUN = "SimSun.ttc"; // 字体-宋体
	private final String SIMHEI = "SimHei.ttf"; // 字体-黑体
	private final String ARIAL = "Arial.ttf"; // 字体-Arial
	private final String ARIAL_B = "ArialBd.ttf"; // 字体-Arial 粗体
	private final String TIMES = "Times.ttf"; // 字体-Arial
	private final String TIMES_B = "TimesBd.ttf"; // 字体-Arial 粗体
	private final String ARIAL_I = "ArialI.ttf"; // 字体-Arial
	private final String SONGT = "SongT.ttf"; // 字体-Arial
	private final String JDHEI = "JDHei.ttf"; // 字体-经典黑体加粗
	private final String MSHEI = "MSHei.ttf"; // 字体-Arial
	private static final String UTF8 = "UTF-8"; // 编码-UTF-8
	// 用于查找条形码的路径和文件
	private static final String PDF_SUFFIX_NAME = "pdf"; // PDF后缀名(文件类型)
	private static final String PIC_SUFFIX_NAME = "jpg"; // PDF后缀名(文件类型)
	private String pdfExprotPath = MessageResources.getValue("app.path.pdf.export");// 生成PDF文件的路径

	private static final Color WATERMARK_FONT_COLOR = Color.LIGHT_GRAY; // 水印字体默认颜色
	private static final int WATERMARK_FONT_SIZE = 60; // 水印默认字体大小
	private static final int ROTATION = 45; // 水印旋转角度
	private static final String PAGETYPE = "pageType"; // 页码类型
	private static final String HEADERLEFT = "headerLeft"; // 页眉位置:左 一般为图片
	private static final String HEADERCENTER = "headerCenter"; // 页眉位置:中
	private static final String HEADERRIGHT = "headerRight"; // 页眉位置:右
	private static final String FOOTERLEFT = "footerLeft"; // 页脚位置：中
	private static final String FOOTERCENTER = "footerCenter";// 页脚位置：中
	private static final String FOOTERRIGHT = "footerRight";// 页脚位置：右
	private static final int HEADER_Y = 35;
	private static final int HEADER_LINE_Y = 40;
	private static final int HEADER_LINE_X = 60;

	/**
	 * 生成申报书条形码.
	 * 
	 * @time：2012.6.13
	 * 
	 * @author huangt
	 * @param code
	 */
	public String createBarCode(String code) throws Exception {

		try {
			// Create the barcode bean
			Code128Bean bean = new Code128Bean();
			final int dpi = 100;
			// Configure the barcode generator
			bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); // makes the narrow
			bean.doQuietZone(false);
			// Open output file
			File filePath = new File(pdfExprotPath);
			// 判断文件夹是否存在,如果不存在则创建文件夹
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String fileName = getTempName(pdfExprotPath, PIC_SUFFIX_NAME);
			File outputFile = new File(fileName);
			OutputStream out = new FileOutputStream(outputFile);
			try {
				// Set up the canvas provider for monochrome JPEG output
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/jpeg", dpi,
						BufferedImage.TYPE_BYTE_BINARY, false, 0);
				// Generate the barcode
				bean.generateBarcode(canvas, code);
				// Signal end of generation
				canvas.finish();
			} finally {
				out.close();
			}
			return fileName;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在pdf文件中添加水印.
	 * 
	 * @author huangt
	 * @time 2012.7.13
	 * @param outputFile
	 *            水印输出文件
	 * @param waterMarkName
	 *            水印名字
	 * @param rotation
	 *            旋转角度
	 */

	public void waterMark(String outputFile, String waterMarkName, int fontSize) throws Exception {
		waterMark(outputFile, waterMarkName, ROTATION, WATERMARK_FONT_COLOR, fontSize, true); // 默认角度为45,默认颜色为浅灰色
	}

	public void waterMark(String outputFile, String waterMarkName, int fontSize, boolean isNoFirst) throws Exception {
		waterMark(outputFile, waterMarkName, ROTATION, WATERMARK_FONT_COLOR, fontSize, isNoFirst); // 默认角度为45,默认颜色为浅灰色
	}

	public void waterMark(String outputFile, String waterMarkName) throws Exception {
		waterMark(outputFile, waterMarkName, true); // 默认角度为45,默认颜色为浅灰色
	}

	public void waterMark(String outputFile, String waterMarkName, Color color) throws Exception {
		waterMark(outputFile, waterMarkName, ROTATION, color, WATERMARK_FONT_SIZE, true); // 默认角度为45
	}

	public void waterMark(String outputFile, String waterMarkName, float rotation) throws Exception {
		waterMark(outputFile, waterMarkName, rotation, WATERMARK_FONT_COLOR, WATERMARK_FONT_SIZE, true); // 默认颜色为浅灰色
	}

	public void waterMark(String outputFile, String waterMarkName, boolean isNoFirst) throws Exception {
		waterMark(outputFile, waterMarkName, ROTATION, WATERMARK_FONT_COLOR, WATERMARK_FONT_SIZE, isNoFirst); // 默认角度为45,默认颜色为浅灰色
	}

	public void waterMark(String outputFile, String waterMarkName, float rotation, Color color, int fontSize,
			boolean isNoFirst) throws Exception {

		int total = 0;
		String tempPdf = getTempName(pdfExprotPath, PDF_SUFFIX_NAME);
		FileUtil.copyFile(new File(outputFile), pdfExprotPath, UUID.randomUUID().toString() + "." + PDF_SUFFIX_NAME,
				true);
		PdfReader reader = new PdfReader(tempPdf);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
		total = reader.getNumberOfPages() + 1;
		PdfContentByte under;
		com.lowagie.text.Font font = FontFactory.getFont(buildAbsoluteFilePath("FONT") + ARIAL, BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED, WATERMARK_FONT_SIZE, Font.PLAIN, color);
		for (int i = 1; i < total; i++) {
			if (isNoFirst && i == 1) {
				continue;
			}
			Rectangle pageSize = reader.getPageSize(i);
			under = stamper.getUnderContent(i);
			under.beginText();
			// 添加水印
			ColumnText.showTextAligned(under, Element.ALIGN_CENTER, new Phrase(waterMarkName, font),
					pageSize.getWidth() / 2, pageSize.getHeight() / 2, rotation);
			under.endText();
		}

		stamper.close();
		// 删除临时文件
		deleteFile(tempPdf);// 删除临时文件
	}

	/**
	 * 
	 * 拼接PDF （可根据所在的页码插入）.
	 * 
	 * @author huangt @time：2012.6.15
	 * 
	 */
	@SuppressWarnings("unused")
	private String mosaicPdf(String subfile, String outputFile, Integer index) throws Exception {
		String outputFileCopy = getTempName(pdfExprotPath, PDF_SUFFIX_NAME);
		// FileUtils.copyFile(outputFile, outputFileCopy); // 复制一个临时文件
		FileUtil.copyFile(new File(outputFile), pdfExprotPath, UUID.randomUUID().toString() + "." + PDF_SUFFIX_NAME,
				true);
		String tempFile = getTempName(pdfExprotPath, PDF_SUFFIX_NAME);
		Document document = new Document();
		// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
		PdfWriter.getInstance(document, new FileOutputStream(tempFile));
		document.open();
		Paragraph chunk = new Paragraph(" ");
		document.add(chunk);
		document.close();
		PdfReader reader = new PdfReader(outputFileCopy);
		PdfReader reader2 = new PdfReader(subfile);
		PdfReader reader3 = new PdfReader(tempFile);

		PdfStamper stamper = new PdfStamper(reader3, new FileOutputStream(outputFile));
		try {
			int total = reader.getNumberOfPages();
			int total2 = reader2.getNumberOfPages();

			PdfContentByte under;

			// 从现有的别的pdf合并过来
			for (int i = 1; i < total + total2; i++) {
				stamper.insertPage(i, PageSize.A4);
				under = stamper.getUnderContent(i);
				if (i > index && i < index + total2) { // 根据index页码在PDF中插入需要插入的PDF
					under.addTemplate(stamper.getImportedPage(reader2, i - index), 1, 0, 0, 1, 0, 0);
				} else {
					if (i >= index + total2) {
						under.addTemplate(stamper.getImportedPage(reader, i - total2), 1, 0, 0, 1, 0, 0);
					} else {
						under.addTemplate(stamper.getImportedPage(reader, i), 1, 0, 0, 1, 0, 0);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stamper.close();
			deleteFile(tempFile); // 删除临时文件
			deleteFile(outputFileCopy);
		}
		return null;
	}

	/**
	 * 
	 * 合并PDF.
	 * 
	 * @author huangt @time：2012.6.15
	 * @param list
	 *            需要合并的PDF路径
	 * @param fileName
	 *            合并生成的PDF文件名
	 */
	public String mosaicPdf(List<String> list, String fileName) throws Exception {

		if (list != null && list.size() > 0) {
			String savePath = getFileName(pdfExprotPath, fileName, PDF_SUFFIX_NAME);
			Document document = new Document(new PdfReader(list.get(0)).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(savePath));
			document.open();
			for (int i = 0; i < list.size(); i++) {
				PdfReader reader = new PdfReader(list.get(i));
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();
			for (String str : list) {
				if (str.indexOf("pdf/") != -1) {
					deleteFile(str);
				}
			}
			return savePath;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * 设置页眉和页脚.
	 * 
	 * @author huangt
	 * @throws IOException
	 * @param pdfFile
	 *            需要设置页眉页码的pdf文件
	 * @param header
	 *            页眉内容
	 * @param isHomePage
	 *            是否首页显示页码 @time：2012.6.15
	 * 
	 */
	public void setPDFHeader(String pdfFile, Map<String, Object> header) throws IOException {
		setPDFHeader(pdfFile, header, 0, true, false); // 默认首页不显示页码
	}

	public void setPDFHeader(String pdfFile, Map<String, Object> header, boolean isNoHomePage, boolean isNoLastPape)
			throws IOException {
		setPDFHeader(pdfFile, header, 0, isNoHomePage, isNoLastPape); // 默认首页不显示页码
	}

	/**
	 * 设置页眉和页脚.
	 * 
	 * @param pdfFile
	 *            需要设置页眉页码的pdf文件
	 * @param header
	 *            页眉页脚内容
	 * @param isNoHomePage
	 *            首页是否不显示页码
	 * @param isNoLastPape
	 *            尾页是否不显示页码
	 * @throws IOException
	 */
	public void setPDFHeader(String pdfFile, Map<String, Object> header, Integer fundPages, boolean isNoHomePage,
			boolean isNoLastPape) throws IOException {
		int total = 0;
		String tempPdf = getTempName(pdfExprotPath, PDF_SUFFIX_NAME);
		try {
			FileUtil.copyFile(new File(pdfFile), pdfExprotPath, UUID.randomUUID().toString() + "." + PDF_SUFFIX_NAME,
					true);
			PdfReader reader = new PdfReader(tempPdf);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdfFile));
			total = reader.getNumberOfPages();
			PdfContentByte under;
			BaseFont bf = BaseFont.createFont(buildAbsoluteFilePath("FONT") + SIMHEI, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			BaseFont bfArial = BaseFont.createFont(buildAbsoluteFilePath("FONT") + SONGT, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			int totalNum = total; // 总页码数
			if (fundPages != null) {
				totalNum = totalNum - fundPages;
			} else {
				fundPages = 0;
			}
			if (isNoLastPape) { // 末页是否要页码,true 为不需要页码，则总页码减1
				totalNum = totalNum - 1;
			}
			if (isNoHomePage) { // 首页是否要页码,true 为不需要页码，则总页码减1
				totalNum = totalNum - 1;
			}
			String pageType = ObjectUtils.toString(header.get(PAGETYPE));
			if (pageType == null || "".equals(pageType)) {
				pageType = " 第 @current@ 页  共 @total@ 页 ";
			}
			for (int i = 1; i <= total; i++) {
				// 增加内容
				Rectangle pageSize = reader.getPageSize(i);
				under = stamper.getUnderContent(i);
				under.beginText();
				if (i == 1) { // 设置版本号
					if (header.containsKey("version")) {
						under.setFontAndSize(bfArial, 10);
						under.showTextAlignedKerned(Element.ALIGN_CENTER, ObjectUtils.toString(header.get("version")),
								pageSize.getWidth() - HEADER_LINE_X - HEADER_LINE_X, HEADER_Y, 0);
					}
				}
				under.setFontAndSize(bfArial, 9);
				if (isNoHomePage) { // 判断首页是否要页码
					if (i > 1) { //
						if ((i - 1) <= totalNum) {
							under.showTextAlignedKerned(Element.ALIGN_CENTER,
									pageType.replace("@current@", (i - 1) + "").replace("@total@", totalNum + ""),
									pageSize.getWidth() / 2, HEADER_Y, 0);
						} else if (!isNoLastPape) {
							under.showTextAlignedKerned(Element.ALIGN_CENTER, pageType
									.replace("@current@", (i - 1 - totalNum) + "").replace("@total@", fundPages + ""),
									pageSize.getWidth() / 2, HEADER_Y, 0);
						}
					}

				} else {
					if (i <= totalNum) {
						under.showTextAlignedKerned(Element.ALIGN_CENTER,
								pageType.replace("@current@", i + "").replace("@total@", totalNum + ""),
								pageSize.getWidth() / 2, HEADER_Y, 0);
					} else if (!isNoHomePage) {
						under.showTextAlignedKerned(Element.ALIGN_CENTER,
								pageType.replace("@current@", (i - totalNum) + "").replace("@total@", fundPages + ""),
								pageSize.getWidth() / 2, HEADER_Y, 0);
					}
				}
				if (i > 1 && i < total) {
					under.setFontAndSize(bf, 10);
					setHeaderByMap(under, pageSize, header, true, true);
					under.saveState();
					under.setLineWidth(0.5f);
					under.moveTo(50, pageSize.getHeight() - HEADER_LINE_Y);
					under.lineTo(pageSize.getWidth() - 50, pageSize.getHeight() - HEADER_LINE_Y);
					under.stroke();
					under.restoreState();
				} else {
					under.setFontAndSize(bf, 10);
					if (i == total) {
						if (isNoLastPape) {
							setHeaderByMap(under, pageSize, header, false, true);
						} else {
							setHeaderByMap(under, pageSize, header, true, true);
						}
						under.saveState();
						under.setLineWidth(0.5f);
						under.moveTo(HEADER_LINE_X, pageSize.getHeight() - HEADER_LINE_Y);
						under.lineTo(pageSize.getWidth() - HEADER_LINE_X, pageSize.getHeight() - HEADER_LINE_Y);

					} else if (i == 1) {
						setHeaderByMap(under, pageSize, header, false, false);
						under.saveState();
					}
					under.stroke();
					under.restoreState();
				}
				under.endText();
			}
			stamper.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		deleteFile(tempPdf);
	}

	/**
	 * 根据页眉页脚入参（位置，类型:用以@开头）设置页眉页脚.
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * 
	 */
	public void setHeaderByMap(PdfContentByte under, Rectangle pageSize, Map<String, Object> header, boolean isFoot,
			boolean isHeard) throws Exception {
		if (isHeard) {
			String headerLeft = ObjectUtils.toString(header.get(HEADERLEFT));
			String headerCenter = ObjectUtils.toString(header.get(HEADERCENTER));
			String headerRight = ObjectUtils.toString(header.get(HEADERRIGHT));
			// 页眉左边
			if (isPath4Header(headerLeft)) {
				setHeaderByImage(under, pageSize, HEADER_LINE_X, pageSize.getHeight() - HEADER_Y,
						getImagePath(headerLeft));
			} else {
				under.showTextAlignedKerned(Element.ALIGN_LEFT, headerLeft, HEADER_LINE_X,
						pageSize.getHeight() - HEADER_Y, 0);
			}
			// 页眉中间
			if (isPath4Header(headerCenter)) {
				setHeaderByImage(under, pageSize, pageSize.getWidth() / 2, pageSize.getHeight() - HEADER_Y,
						getImagePath(headerCenter));
			} else {
				under.showTextAlignedKerned(Element.ALIGN_CENTER, headerCenter, pageSize.getWidth() / 2,
						pageSize.getHeight() - HEADER_Y, 0);
			}
			// 页眉右边
			if (isPath4Header(headerRight)) {
				setHeaderByImage(under, pageSize, pageSize.getWidth() - HEADER_LINE_X, pageSize.getHeight() - HEADER_Y,
						getImagePath(headerRight), true);
			} else {
				under.showTextAlignedKerned(Element.ALIGN_RIGHT, headerRight, pageSize.getWidth() - HEADER_LINE_X,
						pageSize.getHeight() - HEADER_Y, 0);
			}

		}
		if (isFoot) {
			String footerLeft = ObjectUtils.toString(header.get(FOOTERLEFT));
			String footerCenter = ObjectUtils.toString(header.get(FOOTERCENTER));
			String footerRight = ObjectUtils.toString(header.get(FOOTERRIGHT));
			// 页脚左边
			if (isPath4Header(footerLeft)) {
				setHeaderByImage(under, pageSize, HEADER_LINE_X, HEADER_Y, getImagePath(footerLeft));
			} else {
				under.showTextAlignedKerned(Element.ALIGN_LEFT, footerLeft, HEADER_LINE_X, HEADER_Y, 0);
			}
			// 页脚中间
			if (isPath4Header(footerCenter)) {
				setHeaderByImage(under, pageSize, pageSize.getWidth() / 2, HEADER_Y, getImagePath(footerCenter));
			} else {
				under.showTextAlignedKerned(Element.ALIGN_CENTER, footerCenter, pageSize.getWidth() / 2, HEADER_Y, 0);
			}
			// 页脚右边
			if (isPath4Header(footerRight)) {
				setHeaderByImage(under, pageSize, pageSize.getWidth() - HEADER_LINE_X, HEADER_Y,
						getImagePath(footerRight), true);
			} else {
				under.showTextAlignedKerned(Element.ALIGN_RIGHT, footerRight, pageSize.getWidth() - HEADER_LINE_X,
						HEADER_Y, 0);
			}
		}
	}

	public void setHeaderByImage(PdfContentByte under, Rectangle pageSize, float x, float y, String imagePath)
			throws Exception {
		if (imagePath != null && !"".equals(imagePath)) {
			Image image = Image.getInstance(imagePath);
			image.setAbsolutePosition(x, y);
			under.addImage(image);
		}
	}

	public void setHeaderByImage(PdfContentByte under, Rectangle pageSize, float x, float y, String imagePath,
			boolean isRight) throws Exception {
		if (imagePath != null && !"".equals(imagePath)) {
			Image image = Image.getInstance(imagePath);
			if (isRight) {
				image.setAbsolutePosition(x - image.getWidth(), y);
			} else {
				image.setAbsolutePosition(x, y);
			}
			under.addImage(image);
		}
	}

	/**
	 * 页眉页脚插入图时,判断是否是图片路径,并返回图片的路径(因为传过来的值是以@开头的为路径).
	 */
	public boolean isPath4Header(String headerStr) {
		if (headerStr.startsWith("@")) {
			return true;
		}
		return false;
	}

	public String getImagePath(String headerStr) {
		return headerStr.substring(1);
	}

	public static void main(String[] args) {
		ExportPdfFileUtil pdfFileUtil=new ExportPdfFileUtil();
		pdfFileUtil.exportPdfFile("www.baidu.com", new HashMap<String, Object>());
	}
	
	/**
	 * 生成PDF.
	 * 
	 * @param urlStr
	 *            要生成PDF的页面URL
	 * @param map
	 *            页面需要用到的参数
	 * @return
	 * @throws Exception
	 */
	public String exportPdfFile(String urlStr, Map<String, Object> map) {
		String outputFile = getTempName(pdfExprotPath, PDF_SUFFIX_NAME);
		File filePath = new File(pdfExprotPath);
		// urlStr = URLDecoder.decode(urlStr, "UTF-8");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			String str = getHtmlFile(urlStr, map);
			// renderer.setDocument(new File(str));
			renderer.setDocumentFromString(str);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + SIMSUN, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // 字体包
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + SIMHEI, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // 黑体
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + ARIAL, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // Arail
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + ARIAL_B, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // Arail
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + ARIAL_I, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // Arail
			// Times New Roman
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + TIMES, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			// Times New Roman
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + TIMES_B, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + JDHEI, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			fontResolver.addFont(buildAbsoluteFilePath("FONT") + MSHEI, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.layout();
			renderer.createPDF(os);
			os.flush();
			os.close();
			// 返回生成PDF文件的路径和名字 ,以保存数据库
			return outputFile;
		} catch (FileNotFoundException e) {
			// logger.error("不存在文件！" + e.getMessage());
			return null;
		} catch (DocumentException e) {
			// logger.error("生成pdf时出错了！" + e.getMessage());
			return null;
		} catch (IOException e) {
			// logger.error("pdf出错了！" + e.getMessage());
			return null;
		}

	}

	// 读取页面内容
	public static String getHtmlFile(String urlStr, Map<String, Object> map) {
		URL url;
		try {
			if (urlStr.indexOf("?") != -1) {
				urlStr = urlStr + "&locale=" + LocaleContextHolder.getLocale().toString();
			} else {
				urlStr = urlStr + "?locale=" + LocaleContextHolder.getLocale().toString();
			}
			url = new URL(urlStr);

			// URLConnection uc = url.openConnection();
			// uc.addRequestProperty("xmlData", xmlData);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,
			// 默认情况下是false;
			urlConn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			urlConn.setDoInput(true);
			// Post 请求不能使用缓存
			urlConn.setUseCaches(false);
			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			// 设定请求的方法为"POST"，默认是GET
			urlConn.setRequestMethod("POST");

			// 连接，上面对urlConn的所有配置必须要在connect之前完成，
			urlConn.connect();
			// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。

			OutputStream os = urlConn.getOutputStream();
			String param = new String();
			String xmlData = URLEncoder.encode(map.get("xmlData").toString(), "UTF-8");
			if (map.containsKey("barCodePath")) {
				param = "xmlData=" + xmlData + "&jspUrl=" + map.get("jspUrl").toString() + "&barCodePath="
						+ map.get("barCodePath"); // 传参
			} else {
				param = "xmlData=" + xmlData + "&jspUrl=" + map.get("jspUrl").toString(); // 传参
			}
			os.write(param.getBytes());
			InputStream is = urlConn.getInputStream();

			Tidy tidy = new Tidy();

			OutputStream os2 = new ByteArrayOutputStream();
			tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
			tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
			tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
			tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
			tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
			tidy.parse(is, os2);

			is.close();

			// 解决乱码 --将转换后的输出流重新读取改变编码
			String temp;
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(((ByteArrayOutputStream) os2).toByteArray()), UTF8));
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}

			return sb.toString();
		} catch (IOException e) {
			// logger.error("读取客户端网页文本信息时出错了" + e.getMessage());
			return null;
		}

	}

	/**
	 * 
	 * 根据特殊字符查找所在页码.
	 * 
	 * @author huangt
	 * @throws IOException
	 * @time：2012.7.6
	 * 
	 */
	public static int findStringForPageNum(String fileName, String pageString) throws Exception {
		// String PAGESTRING = "#$%@*";
		PdfReader reader = new PdfReader(fileName);
		int pageNum = 0;
		int total = reader.getNumberOfPages();
		String pageStringUnicode = toUnicode(pageString);
		for (int i = 1; i < total; i++) {
			byte[] streamBytes = reader.getPageContent(i);
			PRTokeniser tokenizer = new PRTokeniser(streamBytes);
			StringBuffer sb = new StringBuffer();
			while (tokenizer.nextToken()) {
				if (tokenizer.getTokenType() == PRTokeniser.TK_STRING) {
					String temp;
					System.out.println(tokenizer.getStringValue());
					byte[] b = tokenizer.getStringValue().getBytes("ISO-8859-1");
					for (int j = 0; j < b.length; j++) {
						temp = Integer.toHexString(0xFF & b[j]);
						if (temp.length() < 2) {
							sb.append(0);
						}
						sb.append(temp);
					}
				}
			}
			if (sb.toString().indexOf(pageStringUnicode) > 0) {
				pageNum = i;
				break;
			}
		}
		return pageNum;
	}

	/**
	 * 删除临时文件 fileName:如果传入文件名,即不生成序列化的临时文件名.
	 */
	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		file.delete();
	}

	/**
	 * 生成序列化的临时文件名 fileDir:文件路径 type:文件类型,如jpg pdf 等
	 * fileName:如果传入文件名,即不生成序列化的临时文件名.
	 */
	public static String getTempName(String fileDir, String type) {
		String fileStr = fileDir + UUID.randomUUID().toString() + "." + type;
		return fileStr;
	}

	/**
	 * 生成文件名 fileDir:文件路径 type:文件类型,如jpg pdf 等 fileName:如果传入文件名,即不生成序列化的临时文件名.
	 */
	public static String getFileName(String fileDir, String fileName, String type) {
		String fileStr = fileDir + fileName + "." + type;
		return fileStr;
	}

	public static void parsePdf(String src) throws IOException {
		PdfReader reader = new PdfReader(src);
		byte[] streamBytes = reader.getPageContent(1);
		PRTokeniser tokenizer = new PRTokeniser(streamBytes);
		while (tokenizer.nextToken()) {
			if (tokenizer.getTokenType() == PRTokeniser.TK_STRING) {
				StringBuffer sb = new StringBuffer();
				String sTemp;
				for (int i = 0; i < tokenizer.getStringValue().getBytes("Unicode").length; i++) {
					sTemp = Integer.toHexString(0xFF & tokenizer.getStringValue().getBytes("Unicode")[i]);
					if (sTemp.length() < 2) {
						sb.append(0);
					}
					sb.append(sTemp);
				}
				System.out.println(sb.toString());
			}
		}
	}

	public static String toUnicode(String s) throws Exception {

		byte[] bytes = s.getBytes("Unicode");
		String str;
		StringBuffer sb = new StringBuffer();
		for (int j = 2; j < bytes.length; j++) {
			str = Integer.toHexString(0xFF & bytes[j]);
			if (str.length() < 2) {
				sb.append(0);
			}
			sb.append(str);
		}
		return sb.toString();
	}

	private String buildAbsoluteFilePath(String type) {
		String apath = "";
		if ("PDF".equalsIgnoreCase(type)) {
			apath = pdfExprotPath;
		}
		if ("FONT".equalsIgnoreCase(type)) {
			apath = fontRootPath;
		}
		// if (!apath.endsWith(File.separator)) {
		// apath += File.separator;
		// }
		return apath;
	}

}