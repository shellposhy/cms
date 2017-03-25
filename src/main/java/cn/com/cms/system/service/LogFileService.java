package cn.com.cms.system.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.BaseLog;
import cn.com.cms.framework.base.Result;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 日志文件服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class LogFileService {
	/**
	 * 查询log4j文件日志记录
	 * 
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param query
	 * @param firstResult
	 * @param maxResult
	 * @param desc
	 * @return
	 */
	public Result<BaseLog> searchLog(int type, Date startDate, Date endDate, String query, int firstResult,
			int maxResult, boolean sortType) {
		String logMainRoute1 = null;
		String logMainRoute2 = null;
		try {
			Properties prop = new Properties();
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("log4j.properties");
			prop.load(in);
			if (type == 1) {
				logMainRoute1 = prop.getProperty("log4j.ADMIN.FILE1");
				logMainRoute2 = prop.getProperty("log4j.ADMIN.FILE2");
			} else {
				logMainRoute1 = prop.getProperty("log4j.USER.FILE1");
				logMainRoute2 = prop.getProperty("log4j.USER.FILE2");
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String logRoute1;
		String logRoute2;
		Date today = new Date();
		Calendar todayCal = Calendar.getInstance();
		Calendar dateCal = Calendar.getInstance();
		todayCal.setTime(today);
		dateCal.setTime(startDate);
		List<File> logFileList = new ArrayList<File>();
		while (BaseLog.dateCompare(startDate, endDate) <= 0) {
			if (todayCal.get(Calendar.DATE) == dateCal.get(Calendar.DATE)
					&& todayCal.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)
					&& todayCal.get(Calendar.YEAR) == dateCal.get(Calendar.YEAR)) {
				logRoute1 = logMainRoute1;
				logRoute2 = logMainRoute2;
			} else {
				logRoute1 = logMainRoute1 + DateTimeUtil.format(startDate, "yyyy-MM-dd") + ".log";
				logRoute2 = logMainRoute2 + DateTimeUtil.format(startDate, "yyyy-MM-dd") + ".log";
			}
			File logFile;
			logFile = new File(logRoute1);
			if (logFile.exists()) {
				logFileList.add(logFile);
			}
			logFile = new File(logRoute2);
			if (logFile.exists()) {
				logFileList.add(logFile);
			}
			dateCal.setTime(startDate);
			dateCal.set(Calendar.DATE, dateCal.get(Calendar.DATE) + 1);
			startDate = dateCal.getTime();
		}
		if (logFileList.size() > 0) {
			return getResult(logFileList, query, firstResult, maxResult, sortType);
		}
		return null;
	}

	/**
	 * 读取整个日志文件
	 * 
	 * @param logFiles
	 * @param query
	 * @param firstResult
	 * @param maxResult
	 * @param sortType
	 * @return
	 */
	private Result<BaseLog> getResult(List<File> logFiles, String query, int firstResult, int maxResult,
			boolean sortType) {
		try {
			List<BaseLog> logList = new ArrayList<BaseLog>();
			int totalCount = 0;
			int lastResult = firstResult + maxResult;
			for (File logFile : logFiles) {
				FileInputStream inputStream = new FileInputStream(logFile);
				InputStreamReader isReader = new InputStreamReader(inputStream);
				BufferedReader bReader = new BufferedReader(isReader);
				if (query != null) {
					query = query.toLowerCase();
				}
				List<String> logStrList = new LinkedList<String>();
				while (true) {
					String logStr = bReader.readLine();
					if (logStr == null)
						break;
					logStrList.add(logStr);
				}
				int maxIndex = logStrList.size() - 1;
				if (sortType) {
					for (int i = maxIndex; i >= 0; i--) {
						BaseLog log = BaseLog.formatLog(logStrList.get(i));
						if (query == null || BaseLog.isMatchQuery(query, log)) {
							totalCount++;
							if (totalCount >= firstResult && totalCount <= lastResult) {
								logList.add(log);
							}
						}
					}
				} else {
					for (int i = 0; i <= maxIndex; i++) {
						BaseLog log = BaseLog.formatLog(logStrList.get(i));
						if (query == null || BaseLog.isMatchQuery(query, log)) {
							totalCount++;
							if (totalCount >= firstResult && totalCount <= lastResult) {
								logList.add(log);
							}
						}
					}
				}
				bReader.close();
				isReader.close();
				inputStream.close();
			}
			if (logList.size() > 0) {
				Result<BaseLog> result = new Result<BaseLog>();
				result.setList(logList);
				result.setTotalCount(totalCount);
				return result;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
