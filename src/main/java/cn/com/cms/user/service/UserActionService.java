package cn.com.cms.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.framework.base.tree.MenuTreeNode;
import cn.com.cms.framework.base.tree.DefaultTreeNode.PropertySetter;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.user.constant.EActionUserType;
import cn.com.cms.user.dao.UserActionMapMapper;
import cn.com.cms.user.dao.UserActionMapper;
import cn.com.cms.user.dao.UserGroupMapper;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserAction;
import cn.com.cms.user.model.UserGroup;

/**
 * 权限服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class UserActionService {
	private static final Logger log = Logger.getLogger(UserActionService.class);
	@Resource
	private UserActionMapper userActionMapper;
	@Resource
	private UserGroupMapper userGroupMapper;
	@Resource
	private UserActionMapMapper userActionMapMapper;
	@Resource
	private AppConfig appConfig;

	// 定义参数
	private static List<UserAction> allActionList = null;

	@PostConstruct
	private void init() {
		try {
			allActionList = userActionMapper.findByType(EActionUserType.Admin.ordinal());
		} catch (BadSqlGrammarException e) {
			return;
		}
	}

	/**
	 * 获得用户组的权限列表
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Integer> findAdminActionByGroupId(Integer groupId) {
		return userActionMapMapper.findAdminActionIdsByGroupId(groupId);
	}

	/**
	 * 根据用户组获得权限树
	 * 
	 * @param groupId
	 * @return
	 */
	public DefaultTreeNode findAdminTreeByGroup(Integer groupId) {
		DefaultTreeNode treeNode = null;
		List<UserAction> userActionList = userActionMapper.findByType(EActionUserType.Admin.ordinal());
		if (groupId == null) {
			treeNode = DefaultTreeNode.parseTree(userActionList);
		} else {
			final UserGroup userGroup = userGroupMapper.find(groupId);
			final List<Integer> ids = userActionMapMapper.findAdminActionIdsByGroupId(groupId);
			treeNode = DefaultTreeNode.parseTree(DefaultTreeNode.class, userActionList,
					new PropertySetter<DefaultTreeNode, UserAction>() {
						public void setProperty(DefaultTreeNode node, UserAction entity) {
							if (userGroup.getAllAdminAuthority() || entity != null && ids.contains(entity.getId())) {
								node.checked = true;
							}
						}
					});
		}
		return treeNode;
	}

	/**
	 * 获得用户的权限树
	 * 
	 * @param ct
	 * @param user
	 * @param setter
	 * @return
	 */
	public <T extends DefaultTreeNode> T findActionByUser(Class<T> ct, User user,
			final ActionPropertySetter<T> setter) {
		log.info("==========user.action.tree==========");
		T treeNode = findTreeByUser(ct, user, setter);
		return DefaultTreeNode.partTree(treeNode);
	}

	/**
	 * 判断当前登录用户有没有访问权限
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	public boolean isHaveAccessPrivilege(HttpServletRequest request, User user) {
		log.debug("====user.action.access.privilege====");
		boolean hasPrivilege = false;
		String uri = request.getRequestURI();
		if (uri.startsWith("/page")) {
			hasPrivilege = true;
		} else if (uri.startsWith("/admin")) {
			List<String> excludeUrls = appConfig.getExcludeUrls();
			if (null != excludeUrls && excludeUrls.size() > 0) {
				for (String excludeUrl : excludeUrls) {
					if (uri.equals(excludeUrl)) {
						hasPrivilege = true;
						break;
					}
				}
			}
			// 权限验证
			if (!hasPrivilege) {
				if (null != user) {
					List<String> currentUserActionList = Lists.newArrayList();
					List<UserGroup> groups = userGroupMapper.findByUserId(user.getId());
					if (null != groups && groups.size() > 0) {
						for (UserGroup group : groups) {
							if (group.getAllAdminAuthority()) {
								hasPrivilege = true;
								break;
							}
						}
						if (!hasPrivilege) {
							for (UserGroup group : groups) {
								List<Integer> actionIds = userActionMapMapper
										.findAdminActionIdsByGroupId(group.getId());
								List<UserAction> actions = null != actionIds && actionIds.size() > 0
										? userActionMapper.findAdminByIds(actionIds) : null;
								createUserActionList(currentUserActionList, actions);
							}
						}
					}
					// 权限匹配
					for (String str : currentUserActionList) {
						log.debug("===user action=>=" + str);
						if (Pattern.matches(str, uri)) {
							hasPrivilege = true;
							break;
						}
					}
				}
			}
		}
		return hasPrivilege;
	}

	/**
	 * 把权限列表放入列表
	 * 
	 * @param result
	 * @param list
	 * @return
	 */
	public List<String> createUserActionList(List<String> result, List<UserAction> list) {
		if (null != list && list.size() > 0) {
			for (UserAction userAction : list) {
				String action = userAction.getUri();
				String[] actions = action.split(SystemConstant.COMMA_SEPARATOR);
				if (null != actions && actions.length > 0) {
					for (String str : actions) {
						if (!str.equals("#")) {
							if (!isContainElement(result, str)) {
								result.add(str);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 权限列表是否包含元素
	 * 
	 * @param list
	 * @param element
	 * @return
	 */
	public boolean isContainElement(List<String> list, String element) {
		return list.contains(element);
	}

	/**
	 * 系统用户登录时，获得菜单树
	 * 
	 * @param user
	 * @return
	 */
	public MenuTreeNode getMenuTree(User user) {
		return findTreeByUser(MenuTreeNode.class, user, new ActionPropertySetter<MenuTreeNode>() {
			public void set(MenuTreeNode node, UserAction entity) {
				if (entity != null) {
					if ("#".equals(entity.getUri())) {
						node.uri = entity.getUri();
					} else {
						node.setUri(entity.getUri());
					}
					node.iconSkin = entity.getIconSkin();
					if (null == node.iconSkin) {
						node.iconSkin = "";
					}
				}
			}
		});
	}

	/**
	 * 把权限转换为对应的权限树
	 * 
	 * @param ct
	 * @param user
	 * @param setter
	 * @return
	 */
	public <T extends DefaultTreeNode> T findTreeByUser(Class<T> ct, final User user,
			final ActionPropertySetter<T> setter) {
		List<UserAction> userActionList = null;
		boolean allAdminAuthority = false;
		final List<Integer> ids = new ArrayList<Integer>();
		if ("sa".equals(user.getName())) {
			allAdminAuthority = true;
		} else {
			List<UserGroup> groupList = userGroupMapper.findByUserId(user.getId());
			for (UserGroup group : groupList) {
				if (group.getAllAdminAuthority()) {
					allAdminAuthority = true;
					break;
				}
				List<Integer> actionIds = userActionMapMapper.findActionIdsByGroupId(group.getId());
				if (null != actionIds && actionIds.size() > 0) {
					for (Integer actionId : actionIds) {
						if (!ids.contains(actionId)) {
							ids.add(actionId);
						}
					}
				}
			}
		}
		final boolean allAction = allAdminAuthority;
		if (allAction) {
			userActionList = allActionList;
		} else {
			userActionList = userActionMapper.findAdminByIds(ids);
		}
		T treeNode = DefaultTreeNode.parseTree(ct, userActionList, new PropertySetter<T, UserAction>() {
			public void setProperty(T node, UserAction entity) {
				if (allAction || (entity != null && ids.contains(entity.getId()))) {
					node.checked = true;
				}
				setter.set(node, entity);
			}
		});
		return treeNode;
	}

	/**
	 * 属性容器
	 */
	public interface ActionPropertySetter<K extends DefaultTreeNode> {
		void set(K k, UserAction entity);
	}
}
