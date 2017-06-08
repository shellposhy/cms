package cn.com.cms.framework.esb.jms.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import cn.com.cms.framework.base.Log;
import cn.com.cms.system.contant.ELogAction;
import cn.com.cms.system.contant.ELogTargetType;
import cn.com.cms.system.model.RecordVisit;
import cn.com.cms.system.service.RecordVisitService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;
import cn.com.cms.user.service.UserGroupService;
import cn.com.cms.user.service.UserService;

/**
 * 日志服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class LogRecordListener implements MessageListener {
	private static final String MESSAGE_TYPE = "MESSAGE_TYPE";
	private static final byte ADMIN_LOG = 1;
	private static final byte USER_LOG = 2;

	private final Logger adminLogger = Logger.getLogger("cn.com.cms.log.admin.Logger");
	private final Logger userLogger = Logger.getLogger("cn.com.cms.log.user.Logger");
	@Resource
	private JmsTemplate JmsTemplate;
	@Resource
	private UserService userService;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private RecordVisitService recordVisitService;
	private Queue queue;

	/**
	 * 后台日志
	 * 
	 * @param log
	 * @return
	 */
	public void logAdmin(final Log log) {
		JmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(log);
				message.setByteProperty(MESSAGE_TYPE, ADMIN_LOG);
				return message;
			}
		});
	}

	/**
	 * 前台日志
	 * 
	 * @param log
	 * @return
	 */
	public void logUser(final Log log) {
		JmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(log);
				message.setByteProperty(MESSAGE_TYPE, USER_LOG);
				return message;
			}
		});
	}

	/**
	 * Jms 消息处理类
	 * 
	 * @param message
	 * @return
	 */
	public void onMessage(Message message) {
		ObjectMessage om = null;
		Log log = null;
		Logger logger = null;
		try {
			byte msgType = message.getByteProperty(MESSAGE_TYPE);
			if (msgType == ADMIN_LOG || msgType == USER_LOG) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				om = (ObjectMessage) message;
				log = (Log) om.getObject();
				log.setLogAction(getAccessType(log.getUri(), log.getMethod(), log.getId()));
				log.setLogTargetType(getTargetType(log.getUri(), log.getUrl()));
				if ((log.getLogTargetType().equals(ELogTargetType.DataBase))) {
					char[] uri = log.getUri().toCharArray();
					int firstIdx = 0, secondIdx = 0, idxCount = 0;
					for (int i = uri.length - 1; i >= 0; i--) {
						if (uri[i] == '/') {
							if (idxCount == 0)
								secondIdx = i;
							else {
								firstIdx = i;
								break;
							}
							idxCount++;
						}
					}
					if (firstIdx != 0 && secondIdx != 0) {
						String idStr = log.getUri().substring(firstIdx + 1, secondIdx);
						Integer id = null;
						try {
							id = Integer.valueOf(idStr);
						} catch (Exception e) {
						}
						log.setId(id);
					}
				}
				if (log.getTargetName() == null) {
					if (log.getId() != null) {
						log.setTarget(log.getId());
						if (log.getLogTargetType().equals(ELogTargetType.User)) {
							User user = userService.find(log.getTarget());
							if (user != null) {
								log.setTargetName(user.getName());
							}
						} else if (log.getLogTargetType().equals(ELogTargetType.UserGroup)) {
							UserGroup userGroup = userGroupService.find(log.getTarget());
							if (userGroup != null)
								log.setTargetName(userGroup.getName());
						} else if (log.getLogTargetType().equals(ELogTargetType.DataBase)) {
						}
					} else {
						log.setTargetName("null");
					}
				}
				switch (msgType) {
				case ADMIN_LOG:
					logger = adminLogger;
					break;
				case USER_LOG:
					logger = userLogger;
					break;
				default:
					logger = adminLogger;
				}
				// 写入访问记录
				writeRecordVisit(log);
				try {
					logger.info(log.toString());
				} catch (Exception e) {
				}
			}
		} catch (NumberFormatException e) {
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	/**
	 * 写入访问日志
	 * 
	 * @param log
	 * @return
	 */
	public void writeRecordVisit(Log log) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		RecordVisit record = new RecordVisit();
		record.setUserId(log.getUserId());
		record.setAction(log.getLogAction());
		record.setTarget(log.getLogTargetType());
		record.setVisitTime(new Date());
		record.setYear(cal.get(Calendar.YEAR));
		record.setMonth(cal.get(Calendar.MONTH) + 1);
		record.setDay(cal.get(Calendar.DATE));
		record.setHour(cal.get(Calendar.HOUR_OF_DAY));
		recordVisitService.insert(record);
	}

	/**
	 * 通过uri判断用户操作目标类型
	 * 
	 * @param uri
	 * @param url
	 * @return
	 */
	private ELogTargetType getTargetType(String uri, String url) {
		if (uri.contains("/log/") || uri.contains("/report")) {
			return ELogTargetType.Log;
		}
		if (uri.contains("/user/") || uri.endsWith("/user")) {
			return ELogTargetType.User;
		}
		if (uri.contains("/userGroup/") || uri.endsWith("/userGroup")) {
			return ELogTargetType.UserGroup;
		}
		if (uri.endsWith("/admin")) {
			return ELogTargetType.Sys;
		}
		if (Pattern.matches(".+/page/.+/\\d+_\\d+", uri)) {
			return ELogTargetType.Article;
		}
		return ELogTargetType.Sys;
	}

	/**
	 * 通过uri判断用户行为类型
	 * 
	 * @param uri
	 * @param method
	 * @param id
	 * @return
	 */
	private ELogAction getAccessType(String uri, String method, Integer id) {
		if (uri.endsWith("/s") || uri.endsWith("/qs") || uri.endsWith("/queryStr")) {
			return ELogAction.Search;
		}
		if (uri.endsWith("/admin")) {
			return ELogAction.Login;
		}
		if (uri.endsWith("/edit")) {
			return ELogAction.Edit;
		}
		if (uri.endsWith("/delete")) {
			return ELogAction.Delete;
		}
		if (uri.contains("/download")) {
			return ELogAction.Download;
		}
		if (uri.endsWith("/info") || Pattern.matches(".+/page/.+/\\d+_\\d+", uri)) {
			return ELogAction.View;
		}
		if (uri.endsWith("/print")) {
			return ELogAction.Print;
		}
		if (uri.endsWith("/list") || uri.endsWith("/tree")) {
			return ELogAction.Search;
		}
		if (method.equals("POST")) {
			if (id == null) {
				return ELogAction.SaveInsert;
			}
			return ELogAction.SaveUpdate;
		}
		return ELogAction.Search;
	}
}
