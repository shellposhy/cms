package cn.com.cms.data.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转换拼音
 * 
 * @author shishb
 * 
 */
public class PinyinUtil {
	/**
	 * 汉字转换位汉语拼音首字母
	 * 
	 * @param character
	 * @return
	 */
	public static String converterToFirstSpell(String character) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = character.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if (strs != null) {
						for (int j = 0; j < strs.length; j++) {
							pinyinName.append(strs[j].charAt(0));
							if (j != strs.length - 1) {
								pinyinName.append(",");
							}
						}
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}

	/**
	 * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
	 * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
	 * ,chongdangshen,zhongdangshen,chongdangcan）
	 * 
	 * @param character
	 * @return
	 */
	public static String converterToSpell(String character) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = character.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if (strs != null) {
						for (int j = 0; j < strs.length; j++) {
							pinyinName.append(strs[j]);
							if (j != strs.length - 1) {
								pinyinName.append(",");
							}
						}
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}

	/**
	 * 去除多音字重复数据
	 * 
	 * @param theStr
	 * @return
	 */
	private static List<Map<String, Integer>> discountTheChinese(String theStr) {
		List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> onlyOne = null;
		String[] firsts = theStr.split(" ");
		for (String str : firsts) {
			onlyOne = new Hashtable<String, Integer>();
			String[] china = str.split(",");
			for (String s : china) {
				Integer count = onlyOne.get(s);
				if (count == null) {
					onlyOne.put(s, new Integer(1));
				} else {
					onlyOne.remove(s);
					count++;
					onlyOne.put(s, count);
				}
			}
			mapList.add(onlyOne);
		}
		return mapList;
	}

	/**
	 * 解析并组合拼音，对象合并方案
	 * 
	 * @param list
	 * @return
	 */
	private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
		Map<String, Integer> first = null;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Integer> temp = new Hashtable<String, Integer>();
			if (first != null) {
				for (String s : first.keySet()) {
					for (String s1 : list.get(i).keySet()) {
						String str = s + s1;
						temp.put(str, 1);
					}
				}
				if (temp != null && temp.size() > 0) {
					first.clear();
				}
			} else {
				for (String s : list.get(i).keySet()) {
					String str = s;
					temp.put(str, 1);
				}
			}
			if (temp != null && temp.size() > 0) {
				first = temp;
			}
		}
		String returnStr = "";
		if (first != null) {
			for (String str : first.keySet()) {
				returnStr += (str + ",");
			}
		}
		if (returnStr.length() > 0) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	/**
	 * 解析并组合拼音，循环读取方案（不灵活，不推荐使用）
	 * 
	 * 现在有如下几个数组: {1,2,3} {4,5} {7,8,9} {5,2,8}
	 * 要求写出算法对以上数组进行数据组合,如:1475,1472,1478
	 * ,1485,1482....如此类推，得到的组合刚好是以上数组的最隹组合（不多不少）.
	 * 注：要求有序组合（并非象“全排列算法”那般得到的组合是无序的）：组合过程中，第一组数组排第一位、第二组排第二位、第三组排第三位....
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String parseTheChineseByFor(List<Map<String, Integer>> list) {
		StringBuffer sbf = new StringBuffer();
		int size = list.size();
		switch (size) {
		case 1:
			for (String s : list.get(0).keySet()) {
				String str = s;
				sbf.append(str + ",");
			}
			break;
		case 2:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					String str = s + s1;
					sbf.append(str + ",");
				}
			}
			break;
		case 3:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					for (String s2 : list.get(2).keySet()) {
						String str = s + s1 + s2;
						sbf.append(str + ",");
					}
				}
			}
			break;
		case 4:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					for (String s2 : list.get(2).keySet()) {
						for (String s3 : list.get(3).keySet()) {
							String str = s + s1 + s2 + s3;
							sbf.append(str + ",");
						}
					}
				}
			}
			break;
		case 5:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					for (String s2 : list.get(2).keySet()) {
						for (String s3 : list.get(3).keySet()) {
							for (String s4 : list.get(4).keySet()) {
								String str = s + s1 + s2 + s3 + s4;
								sbf.append(str + ",");
							}
						}
					}
				}
			}
			break;
		case 6:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					for (String s2 : list.get(2).keySet()) {
						for (String s3 : list.get(3).keySet()) {
							for (String s4 : list.get(4).keySet()) {
								for (String s5 : list.get(5).keySet()) {
									String str = s + s1 + s2 + s3 + s4 + s5;
									sbf.append(str + ",");
								}
							}
						}
					}
				}
			}
			break;
		case 7:
			for (String s : list.get(0).keySet()) {
				for (String s1 : list.get(1).keySet()) {
					for (String s2 : list.get(2).keySet()) {
						for (String s3 : list.get(3).keySet()) {
							for (String s4 : list.get(4).keySet()) {
								for (String s5 : list.get(5).keySet()) {
									for (String s6 : list.get(6).keySet()) {
										String str = s + s1 + s2 + s3 + s4 + s5 + s6;
										sbf.append(str + ",");
									}
								}
							}
						}
					}
				}
			}
			break;
		}
		String str = sbf.toString();
		return str.substring(0, str.length() - 1);
	}

	public static void main(String[] args) {
		// 重当参差 重庆的j 刘煜,帕哈丁
		String str = "颐和园";
		System.out.println(converterToFirstSpell(str));
		System.out.println(converterToSpell(str));
	}
}
