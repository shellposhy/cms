package cn.com.cms.data.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import cn.com.cms.data.model.DataField;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.library.constant.EDataType;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.HtmlUtil;
import cn.com.pepper.common.PepperResult;
import cn.com.pepper.comparator.base.PepperSortField.FieldType;

/**
 * 数据工具类
 * 
 * @author shishb
 * @version 1.0
 */
public class DataUtil {
	/**
	 * 根据数值和数据类型获得数据对象
	 * 
	 * @param value
	 *            数据值
	 * @param dataType
	 *            数据类型
	 * @return
	 * @throws ParseException
	 * @throws SerialException
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static Object getDataTypeObject(String value, EDataType dataType) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			if (null == value || value.isEmpty()) {
				return null;
			} else {
				return DateTimeUtil.parse(value);
			}
		case Short:
			return Short.valueOf(value);
		case Int:
			if (null == value || value.isEmpty()) {
				return 0;
			} else {
				return Integer.valueOf(value);
			}
		case Long:
			return Long.valueOf(value);
		case Float:
			return Float.valueOf(value);
		case Double:
		case Numeric:
			return Double.valueOf(value);
		case Bool:
			return Boolean.valueOf(value);
		case Blob:
		case MediumBlob:
			// return new SerialBlob(value.getBytes("utf-8"));
		case Char:
		case Varchar:
		case UUID:
		default:
			return value;
		}
	}

	/**
	 * 根据数据类型获得数据对象的字符串
	 * 
	 * @param object
	 * @param dataType
	 * @return
	 */
	public static String getDataTypeString(Object object, EDataType dataType) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			if ("".equals(object)) {
				return "";
			} else {
				return DateTimeUtil.formatDateTime((Date) object);
			}
		default:
			return null == object ? "" : object.toString();
		}
	}

	/**
	 * 获取数据类型的默认值
	 * 
	 * @param dataType
	 *            数据类型
	 * @param isNotNull
	 *            可否为空
	 * @return
	 */
	public static Object getDefaultValue(EDataType dataType, boolean isNotNull) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			return isNotNull ? DateTimeUtil.getCurrentDateTime() : null;
		case Short:
			return (short) 0;
		case Int:
			return 0;
		case Long:
			return (long) 0;
		case Float:
			return (float) 0;
		case Double:
		case Numeric:
			return (double) 0;
		case Bool:
			return false;
		case Blob:
		case MediumBlob:
		case Char:
		case Varchar:
		case UUID:
		default:
			return "";
		}
	}

	/**
	 * 布尔值转字符串
	 * 
	 * @param blob
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String getString(Blob blob) throws UnsupportedEncodingException, SQLException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(blob.getBinaryStream(), "utf-8"));
		String s = null;
		StringBuilder sb = new StringBuilder();
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		return sb.toString();
	}

	/**
	 * 索引搜索结果转PeopleData列表
	 * 
	 * @param pepperResult
	 * @param fieldList
	 * @return
	 */
	public static List<CmsData> getPeopleDataList(PepperResult pepperResult, List<DataField> fieldList) {
		List<CmsData> result = new ArrayList<CmsData>();
		if (null != pepperResult && null != pepperResult.documents) {
			for (Document doc : pepperResult.documents) {
				result.add(getPeopleData(doc, fieldList));
			}
		}

		return result;
	}

	/**
	 * 索引文档转PeopleData
	 * 
	 * @param doc
	 * @param fieldList
	 * @return
	 */
	public static CmsData getPeopleData(Document doc, List<DataField> fieldList) {
		CmsData peopleData = new CmsData();
		peopleData.setId(Integer.parseInt(doc.get(FieldCodes.ID)));
		peopleData.setTableId(Integer.parseInt(doc.get(FieldCodes.TABLE_ID)));
		peopleData.setBaseId(Integer.parseInt(doc.get(FieldCodes.BASE_ID)));
		for (DataField df : fieldList) {
			if (null == doc.get(df.getCode())) {
				peopleData.put(df.getCode(), "");
			} else {
				switch (df.getDataType()) {
				case Date:
				case Time:
				case DateTime:
					peopleData.put(df.getCode(), DateTimeUtil.parse(doc.get(df.getCode()), "yyyyMMddHHmmss"));
					break;
				default:
					peopleData.put(df.getCode(), getDataTypeObject(doc.get(df.getCode()), df.getDataType()));
					break;
				}
			}

		}

		return peopleData;
	}

	/**
	 * 数据对象转换索引文档
	 * 
	 * @param data
	 *            数据对象
	 * @param fieldList
	 *            字段列表
	 * @return
	 * @throws IOException
	 */
	public static Document getIndexDoc(CmsData data, List<DataField> fieldList) throws IOException {
		NumericField nField = null;
		Date now = new Date();

		Document doc = new Document();
		// TABLE_ID
		nField = new NumericField(FieldCodes.TABLE_ID, Field.Store.YES, true);
		nField.setIntValue(data.getTableId());
		doc.add(nField);

		// BASE_ID
		nField = new NumericField(FieldCodes.BASE_ID, Field.Store.YES, true);
		nField.setIntValue(data.getBaseId());
		doc.add(nField);

		// Index_Time
		nField = new NumericField(FieldCodes.INDEX_TIME, Field.Store.YES, true);
		nField.setLongValue(Long.parseLong(getIndexDateTimeStr(now)));
		doc.add(nField);

		Field.Index indexType = null;
		Field.Store store = null;

		String fieldCode = null;
		boolean toIndex = true;
		for (DataField df : fieldList) {
			toIndex = true;
			switch (df.getIndexType()) {
			case Analyzed:
				indexType = Field.Index.ANALYZED;
				break;
			case AnalyzedNoNorms:
				indexType = Field.Index.ANALYZED_NO_NORMS;
				break;
			case No:
				indexType = Field.Index.NO;
				toIndex = false;
				break;
			case NotAnalyzed:
				indexType = Field.Index.NOT_ANALYZED;
				break;
			case NotAnalyzedNoNorms:
				indexType = Field.Index.NOT_ANALYZED_NO_NORMS;
				break;
			}
			if (df.isIndexStore()) {
				store = Field.Store.YES;
			} else {
				store = Field.Store.NO;
			}
			fieldCode = df.getCode();
			Object value = null;

			value = data.get(fieldCode);

			if (!(store == Field.Store.NO && indexType == Field.Index.NO) && value != null) {
				switch (df.getDataType()) {
				case UUID:
				case Char:
				case Varchar:
				case Blob:
				case MediumBlob:
				case Bool:
					try {
						doc.add(new Field(df.getCode(), HtmlUtil.getText(String.valueOf(value)), store, indexType));
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case Short:
				case IntAutoIncrement:
				case Int:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setIntValue(Integer.parseInt(value.toString()));
					doc.add(nField);
					break;
				case Long:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setLongValue(Long.parseLong(value.toString()));
					doc.add(nField);
					break;
				case Float:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setFloatValue(Float.parseFloat(value.toString()));
					doc.add(nField);
					break;
				case Double:
				case Numeric:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setDoubleValue(Double.parseDouble(value.toString()));
					doc.add(nField);
					break;
				case Date:
				case Time:
				case DateTime:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setLongValue(Long.parseLong(getIndexDateTimeStr((Date) value)));
					doc.add(nField);
					break;
				}
			}
		}
		return doc;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getIndexDateTimeStr(Date date) {
		return DateTimeUtil.format(date, SystemConstant.INDEX_DATE_FORMAT);
	}

	/**
	 * 数据类型转索引类型
	 * 
	 * @param dataType
	 * @return
	 */
	public static String dataType2LuceneType(EDataType dataType) {
		String luceneType = "";
		if (dataType.equals(EDataType.Int) || dataType.equals(EDataType.IntAutoIncrement)
				|| dataType.equals(EDataType.Short) || dataType.equals(EDataType.Numeric)) {
			luceneType = "#int#";
		} else if (dataType.equals(EDataType.Long)) {
			luceneType = "#long#";
		} else if (dataType.equals(EDataType.Float)) {
			luceneType = "#float#";
		} else if (dataType.equals(EDataType.Double)) {
			luceneType = "#double#";
		}
		return luceneType;
	}

	/**
	 * 数据类型转排序类型
	 * 
	 * @param dataType
	 * @return
	 */
	public static FieldType dataType2SortType(EDataType dataType) {
		switch (dataType) {
		case Int:
		case IntAutoIncrement:
			return FieldType.Int;
		case Long:
			return FieldType.Long;
		case Float:
			return FieldType.Float;
		case Double:
			return FieldType.Double;
		case Time:
		case DateTime:
			return FieldType.StringVal;
		// return FieldType.Long;
		default:
			return FieldType.StringVal;

		}
	}

	/**
	 * 获取html里的所有img的src
	 * 
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public static List<String> getImgs(final String html) throws IOException {
		final List<String> imgs = new LinkedList<String>();

		if (null == html || html.isEmpty()) {
			return imgs;
		}

		final Reader r = new StringReader(html);

		ParserDelegator pd = new ParserDelegator();

		pd.parse(r, new HTMLEditorKit.ParserCallback() {

			@Override
			public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
				if (Tag.IMG.equals(t)) {
					imgs.add((String) a.getAttribute(Attribute.SRC));
				}
			}
		}, false);
		r.close();
		if (imgs.size() > 0) {
			return imgs;
		} else {
			return null;
		}

	}

}
