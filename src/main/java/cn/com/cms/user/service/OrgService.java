package cn.com.cms.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.tree.DefaultTreeNode;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.library.constant.EStatus;
import cn.com.cms.user.dao.OrgGroupMapMapper;
import cn.com.cms.user.dao.OrgMapper;
import cn.com.cms.user.dao.UserGroupMapMapper;
import cn.com.cms.user.dao.UserGroupMapper;
import cn.com.cms.user.dao.UserMapper;
import cn.com.cms.user.model.Org;
import cn.com.cms.user.model.OrgGroupMap;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;

/**
 * 机构服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class OrgService {
	@Resource
	private OrgMapper orgMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrgGroupMapMapper orgGroupMapMapper;
	@Resource
	private UserGroupMapMapper userGroupMapMapper;
	@Resource
	private UserService userService;
	@Resource
	private UserGroupMapper userGroupMapper;

	private static List<Org> orgList = null;

	public List<Org> findAll() {
		return orgMapper.findAll();
	}

	public Org find(Integer id) {
		return orgMapper.find(id);
	}

	public Org findByCodeAndParentId(Integer parentId, String code) {
		return orgMapper.findByCodeAndParentId(parentId, code);
	}

	public DefaultTreeNode findTree() {
		if (orgList == null)
			orgList = orgMapper.findAll();
		return DefaultTreeNode.parseTree(orgList);
	}

	public DefaultTreeNode findAllNoUserOrgs() {
		if (orgList == null)
			orgList = orgMapper.findAllNoUserOrgs();
		return DefaultTreeNode.parseTree(orgList);
	}

	public List<Org> findByParentId(int parentId) {
		return orgMapper.findByParentId(parentId);
	}

	public boolean hasChild(int id) {
		List<Org> orgList = orgMapper.findByParentId(id);
		return orgList.size() > 0 ? true : false;
	}

	public void save(Org org) {
		Integer orgId = org.getId();
		org.setOrderID(1);
		org.setUpdateTime(new Date());
		org.setStatus(EStatus.Normal);

		String idsStr = org.getTreeSelId();
		List<Integer> bindGroupIds = null;// 机构绑定的用户组的id
		if (idsStr != null && !idsStr.isEmpty()) {
			String[] ids = idsStr.split(SystemConstant.COMMA_SEPARATOR);
			bindGroupIds = new ArrayList<Integer>();
			for (int i = 0; i < ids.length; i++) {
				bindGroupIds.add(Integer.valueOf(ids[i]));
			}
		}

		if (null != orgId) {
			Org temp = find(orgId);
			temp.setUpdaterId(org.getUpdaterId());
			temp.setUpdateTime(new Date());
			temp.setName(org.getName());
			temp.setCode(org.getCode());
			temp.setParentID(org.getParentID());
			temp.setInherit(org.isInherit());
			orgMapper.update(temp);
			updateOrgGroupMap(temp, bindGroupIds);
		} else {
			org.setCreatorId(org.getUpdaterId());
			org.setCreateTime(new Date());
			orgMapper.insert(org);
			if (bindGroupIds != null) {
				List<OrgGroupMap> orgGroupMapList = new ArrayList<OrgGroupMap>();
				for (Integer groupId : bindGroupIds) {
					OrgGroupMap orgGroupMap = new OrgGroupMap();
					orgGroupMap.setGroupId(groupId);
					orgGroupMap.setOrgId(org.getId());
					orgGroupMapList.add(orgGroupMap);
				}
				if (!orgGroupMapList.isEmpty() && orgGroupMapList.size() > 0) {
					orgGroupMapMapper.batchInsert(orgGroupMapList);
				}
			}
		}
		orgList = orgMapper.findAll();
	}

	private void updateOrgGroupMap(Org org, List<Integer> currentGroupIds) {
		if (currentGroupIds == null)
			currentGroupIds = new ArrayList<Integer>();
		List<Integer> oldGroupIds = orgGroupMapMapper.findGroupIdsByOrgId(org.getId());
		List<Integer> removeIds = new ArrayList<Integer>();
		List<Integer> parentOrgGroupIds = new ArrayList<Integer>();
		getParentGroupIds(org, parentOrgGroupIds);
		for (Integer oldGroupId : oldGroupIds) {
			if (!currentGroupIds.contains(oldGroupId) && !parentOrgGroupIds.contains(oldGroupId)) {
				removeIds.add(oldGroupId);
			}
		}
		List<User> usersInOrg = new ArrayList<User>();
		getUsersInOrg(org.getId(), org.isInherit(), usersInOrg);
		updateUserGroups(usersInOrg, currentGroupIds, removeIds);
		orgGroupMapMapper.deleteByOrgId(org.getId());
		if (currentGroupIds != null) {
			List<OrgGroupMap> orgGroupMapList = new ArrayList<OrgGroupMap>();
			for (Integer groupId : currentGroupIds) {
				OrgGroupMap orgGroupMap = new OrgGroupMap();
				orgGroupMap.setGroupId(groupId);
				orgGroupMap.setOrgId(org.getId());
				orgGroupMapList.add(orgGroupMap);
			}
			if (!orgGroupMapList.isEmpty() && orgGroupMapList.size() > 0) {
				for (OrgGroupMap ogm : orgGroupMapList) {
					orgGroupMapMapper.insert(ogm);
				}
			}
		}
	}

	private void updateUserGroups(List<User> users, List<Integer> orgGroupIds, List<Integer> removeGroupIds) {
		for (User user : users) {
			List<Integer> groupIdsInUser = userGroupMapMapper.findGroupIdsByUserId(user.getId());
			if (orgGroupIds != null) {
				for (Integer groupId : orgGroupIds) {
					if (!groupIdsInUser.contains(groupId)) {
						groupIdsInUser.add(groupId);
					}
				}
			}
			if (removeGroupIds != null) {
				for (Integer groupId : removeGroupIds) {
					if (groupIdsInUser.contains(groupId)) {
						groupIdsInUser.remove(groupId);
					}
				}
			}
			List<UserGroup> userGroupList = new ArrayList<UserGroup>();
			for (Integer groupId : groupIdsInUser) {
				userGroupList.add(userGroupMapper.find(groupId));
			}
			user.setUserGroupList(userGroupList);
			// userService.save(user);
		}

	}

	/**
	 * 查找机构下的所有用户
	 * 
	 * @param orgId
	 * @param isInherit
	 * @param result
	 */
	private void getUsersInOrg(int orgId, boolean isInherit, List<User> result) {
		List<User> users = userMapper.findByOrgId(orgId);
		result.addAll(users);
		if (isInherit) {// 如果需要继承（查询子机构下的用户），那就递归去查找所有子机构
			List<Org> orgChildren = orgMapper.findByParentId(orgId);
			for (Org org : orgChildren) {
				getUsersInOrg(org.getId(), isInherit, result);
			}
		}
	}

	/**
	 * 查找所有父机构绑定的用户组id(父机构的inherit字段为true才能继承到子机构)
	 * 
	 * @param org
	 * @param result
	 */
	public void getParentGroupIds(Org org, List<Integer> result) {
		if (org.getParentID() == null || org.getParentID() == 0)
			return;
		Org parent = orgMapper.find(org.getParentID());
		if (parent.isInherit()) {
			List<Integer> groupIds = orgGroupMapMapper.findGroupIdsByOrgId(org.getParentID());
			for (Integer id : groupIds) {
				if (!result.contains(id)) {
					result.add(id);
				}
			}
		}
		getParentGroupIds(parent, result);
	}

	public void refreshGroupsByUser(User user) {
		Integer orgId = user.getOrgID();
		List<Integer> groupIds = orgGroupMapMapper.findGroupIdsByOrgId(orgId);
		Org org = orgMapper.find(orgId);
		List<Integer> parentGroupIds = new ArrayList<Integer>();
		getParentGroupIds(org, parentGroupIds);
		for (Integer id : parentGroupIds) {
			if (!groupIds.contains(id)) {
				groupIds.add(id);
			}
		}
		List<User> users = new ArrayList<User>();
		users.add(user);
		updateUserGroups(users, groupIds, null);
	}

	public List<Integer> findGroupIdsByOrgId(Integer orgId) {
		return orgGroupMapMapper.findGroupIdsByOrgId(orgId);
	}

	public void delete(Integer id) {
		List<User> users = userMapper.findByOrgId(id);
		if (users.size() == 0) {
			orgMapper.delete(id);
			orgList = orgMapper.findAll();// 更新列表
		}
	}
}
