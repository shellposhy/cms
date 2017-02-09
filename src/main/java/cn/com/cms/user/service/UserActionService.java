package cn.com.cms.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import cn.com.cms.framework.tree.DefaultTreeNode;
import cn.com.cms.framework.tree.DefaultTreeNode.PropertySetter;
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

	// 定义参数
	private static List<UserAction> allActionList = null;

	@PostConstruct
	private void init() {
		try {
			allActionList = userActionMapper.findAll();
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
		List<UserAction> userActionList = userActionMapper.findAdmin();
		if (groupId == null) {
			treeNode = DefaultTreeNode.parseTree(userActionList);
		} else {
			final UserGroup userGroup = userGroupMapper.find(groupId);
			final List<Integer> ids = userActionMapMapper.findAdminActionIdsByGroupId(groupId);
			treeNode = DefaultTreeNode.parseTree(DefaultTreeNode.class, userActionList,
					new PropertySetter<DefaultTreeNode, UserAction>() {
						public void setProperty(DefaultTreeNode node, UserAction entity) {
							if (userGroup.isAllAdminAuthority() || entity != null && ids.contains(entity.getId())) {
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
				if (group.isAllAdminAuthority()) {
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
