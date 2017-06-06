package cn.com.cms.user.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.config.SystemConstant;
import cn.com.cms.framework.esb.cache.impl.IgniteCacheImpl;
import cn.com.cms.user.constant.ELoginType;
import cn.com.cms.user.dao.UserActionMapMapper;
import cn.com.cms.user.dao.UserGroupMapMapper;
import cn.com.cms.user.dao.UserGroupMapper;
import cn.com.cms.user.dao.UserMapper;
import cn.com.cms.user.model.Org;
import cn.com.cms.user.model.User;
import cn.com.cms.user.model.UserGroup;
import cn.com.cms.user.model.UserGroupMap;
import cn.com.people.data.common.exceptions.ParaErrorException;
import cn.com.people.data.util.NetUtil;
import cn.com.people.data.util.PkUtil;
import cn.com.people.data.util.StringUtil;

/**
 * 用户服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class UserService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserGroupMapper userGroupMapper;
	@Resource
	private UserGroupMapMapper userGroupMapMapper;
	@Resource
	private UserActionMapMapper userActionMapMapper;
	@Resource
	private OrgService orgService;
	@Resource
	private IgniteCacheImpl<User> userCache;

	/**
	 * 保存或更新用户
	 * 
	 * @param user
	 *            用户
	 * @return void
	 */
	public void save(User user) {
		if (user.getId() == null) {
			if (user.getUserType() == ELoginType.IP.ordinal()) {
				user.setPassword(StringUtil.encodeToMD5(SystemConstant.PDS_IP_USER_PASSWORD));
			} else if (user.getUserType() == ELoginType.Phone.ordinal()) {
				user.setPassword(StringUtil.encodeToMD5(SystemConstant.PDS_PHONE_USER_PASSWORD));
			}
			userMapper.insert(user);
			updateUserGroupMap(user);
		} else {
			updateUserGroupMap(user);
			if (user.getUserType() == ELoginType.IP.ordinal()) {
				user.setPassword(StringUtil.encodeToMD5(SystemConstant.PDS_IP_USER_PASSWORD));
			} else if (user.getUserType() == ELoginType.Phone.ordinal()) {
				user.setPassword(StringUtil.encodeToMD5(SystemConstant.PDS_PHONE_USER_PASSWORD));
			}
			userMapper.update(user);
		}
	}

	/**
	 * 删除用户(用户设置为非正常状态)
	 * 
	 * @param ids
	 *            用户id数组
	 * @return void
	 */
	public void petrify(Integer[] ids) {
		if (ids.length == 1) {
			User user = userMapper.find(ids[0]);
			if (!SystemConstant.SA_USER.equals(user.getName())) {
				user.setDieName(user.getName());
				user.setName(PkUtil.getShortUUID());
				userGroupMapMapper.deleteByUserId(ids[0]);
				userMapper.petrify(user);
			}
		} else {
			List<User> userList = new ArrayList<User>();
			for (Integer id : ids) {
				User user = userMapper.find(id);
				if (!SystemConstant.SA_USER.equals(user.getName())) {
					userGroupMapMapper.deleteByUserId(id);
					user.setDieName(user.getName());
					user.setName(PkUtil.getShortUUID());
					userList.add(user);
				}
			}
			userMapper.batchPetrify(userList);
		}
	}

	/**
	 * 更新用户组权限
	 * 
	 * @param user
	 * @return
	 */
	private void updateUserGroupMap(User user) {
		if (user.getId() != null)
			userGroupMapMapper.deleteByUserId(user.getId());
		Integer orgId = user.getOrgID();
		List<Integer> groupIds = orgService.findGroupIdsByOrgId(orgId);
		Org org = orgService.find(orgId);
		List<Integer> parentGroupIds = new ArrayList<Integer>();
		orgService.getParentGroupIds(org, parentGroupIds);
		for (Integer id : parentGroupIds) {
			if (!groupIds.contains(id)) {
				groupIds.add(id);
			}
		}
		if ((user.getUserGroupList() != null && user.getUserGroupList().size() != 0) || groupIds.size() > 0) {
			List<UserGroupMap> userGroupMapList = new ArrayList<UserGroupMap>();
			for (UserGroup userGroup : user.getUserGroupList()) {
				UserGroupMap userGroupMap = new UserGroupMap();
				userGroupMap.setGroupID(userGroup.getId());
				userGroupMap.setUserID(user.getId());
				userGroupMapList.add(userGroupMap);
			}
			for (Integer id : groupIds) {
				UserGroupMap userGroupMap = new UserGroupMap();
				userGroupMap.setGroupID(id);
				userGroupMap.setUserID(user.getId());
				userGroupMapList.add(userGroupMap);
			}
			userGroupMapMapper.batchInsert(userGroupMapList);
		}
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param name
	 * @return
	 */
	public User findByName(String name) {
		User user = userMapper.findByName(name);
		if (user != null)
			user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
		return user;
	}

	/**
	 * 根据机构编号查询用户
	 * 
	 * @param orgID
	 * @return
	 */
	public List<User> findByOrgId(int orgID) {
		return userMapper.findByOrgId(orgID);
	}

	/**
	 * 根据机构编号和用户名分页查询用户
	 * 
	 * @param orgID
	 * @param name
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public Result<User> findByOrgAndUserName(int orgID, String name, int firstResult, int maxResult) {
		Result<User> result = new Result<User>();
		result.setList(userMapper.findByOrgAndUserName(orgID, name, firstResult, maxResult));
		result.setTotalCount(userMapper.countByOrgAndUserName(orgID, name));
		return result;
	}

	/**
	 * 真实姓名查询
	 * 
	 * @param realName
	 * @return
	 */
	public List<User> findByRealName(String realName) {
		return userMapper.findByRealName(realName);
	}

	/**
	 * 关键词分页查询所有用户
	 * 
	 * @param qs
	 *            关键词
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 * @return Result
	 */
	public Result<User> searchAllUsers(String qs, int first, int size) {
		Result<User> userResult = new Result<User>();
		userResult.setList(userMapper.searchUsers(qs, first, size));
		userResult.setTotalCount(userMapper.countUsers(qs));
		for (User user : userResult.getList()) {
			user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
		}
		return userResult;
	}

	/**
	 * 关键词分页查询IP用户
	 * 
	 * @param qs
	 *            关键词
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 * @return Result
	 */
	public Result<User> searchIpUsers(String qs, int first, int size) {
		Result<User> ipUserResult = new Result<User>();
		ipUserResult.setList(userMapper.searchIpUsers(qs, first, size));
		ipUserResult.setTotalCount(userMapper.countIpUsers(qs));
		for (User user : ipUserResult.getList()) {
			user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
		}
		return ipUserResult;
	}

	/**
	 * 关键词分页查询密码用户
	 * 
	 * @param qs
	 *            关键词
	 * @param admin
	 *            是否可以登录后台，admin为空查全部
	 * @param first
	 *            第一条
	 * @param size
	 *            页大小
	 * @return Result
	 */
	public Result<User> searchPwUsers(String qs, int first, int size) {
		Result<User> pwUserResult = new Result<User>();
		pwUserResult.setList(userMapper.searchPwUsers(qs, first, size));
		pwUserResult.setTotalCount(userMapper.countPwUsers(qs));
		for (User user : pwUserResult.getList()) {
			user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
		}
		return pwUserResult;
	}

	/**
	 * 判断用户是否有后台管理权限
	 */
	public boolean isAdminAuthority(User user) {
		List<UserGroup> userGroups = userGroupMapper.findByUserId(user.getId());
		for (UserGroup userGroup : userGroups) {
			if (userGroup.isAllAdminAuthority()) {
				return true;
			}
			List<Integer> actionIds = userActionMapMapper.findActionIdsByGroupId(userGroup.getId());
			if (actionIds != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过id查找用户
	 * 
	 * @param id
	 *            用户id
	 * @return User
	 */
	public User find(Integer id) {
		User user = userMapper.find(id);
		List<UserGroup> userGroupList = new ArrayList<UserGroup>();
		userGroupList.addAll(userGroupMapper.findByUserId(id));
		user.setUserGroupList(userGroupList);
		return user;
	}

	/**
	 * 查找所有活着的用户
	 * 
	 * @param qs
	 * @return
	 */
	public List<User> findAlive(String qs) {
		return userMapper.findAlive(qs);
	}

	/**
	 * 根据ip查询用户
	 * 
	 * @param ip
	 * @return
	 */
	public User findPwdIpUserByIp(String ip) {
		List<User> pwdIpUsers = userMapper.searchAllIpPwdUsers();
		return getMatchUserByIp(ip, pwdIpUsers);
	}

	/**
	 * 根据手机号码查询用户
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public User findByPhone(String phoneNumber) {
		if (null == phoneNumber || phoneNumber.trim().isEmpty()) {
			return null;
		}
		return userMapper.findByPhone(phoneNumber);
	}

	/**
	 * 通过ip查找第一个在区间内的ip用户。
	 * 
	 * @param ip
	 * @return {@link User}
	 * @throws ParaErrorException
	 * @throws UnknownHostException
	 */
	public User findByIp(String ip) {
		List<User> ipUsers = userMapper.searchIpUsers(null, 0, 10000);
		return getMatchUserByIp(ip, ipUsers);
	}

	/**
	 * 获取匹配ip地址的用户
	 * 
	 * @param ip
	 * @param users
	 * @return
	 */
	private User getMatchUserByIp(String ip, List<User> users) {
		User curUser = null;
		try {
			Inet4Address curUserIp = NetUtil.toInet4Address(ip);
			for (User user : users) {
				String ipStr = user.getIpAddress();
				if (null != ipStr) {
					String[] ipSegments = ipStr.split(",");
					for (String segment : ipSegments) {
						String[] ips = segment.split("-");
						if (ips.length > 1)
							continue;
						Inet4Address startIp = null, endIp = null;
						startIp = NetUtil.toInet4Address(ips[0]);
						endIp = startIp;
						if (NetUtil.isInTheIPV4Range(startIp, endIp, curUserIp)) {
							user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
							return user;
						}
					}
				}
			}
			for (User user : users) {
				String ipStr = user.getIpAddress();
				if (null != ipStr) {
					String[] ipSegments = ipStr.split(",");
					for (String segment : ipSegments) {
						String[] ips = segment.split("-");
						Inet4Address startIp = null, endIp = null;

						startIp = NetUtil.toInet4Address(ips[0]);
						if (ips.length == 1) {
							endIp = startIp;
						} else {
							endIp = NetUtil.toInet4Address(ips[1]);
						}
						if (NetUtil.isInTheIPV4Range(startIp, endIp, curUserIp)) {
							user.setUserGroupList(userGroupMapper.findByUserId(user.getId()));
							if (curUser == null)
								curUser = user;
							else
								curUser.getUserGroupList().addAll(user.getUserGroupList());
							break;
						}
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ParaErrorException e) {
			e.printStackTrace();
		}
		return curUser;
	}

	/**
	 * 统计所有用户数
	 * 
	 * @return
	 */
	public Integer count() {
		Integer size = userMapper.countIpUsers(null) + userMapper.countPwUsers(null);
		return size;
	}

	/**
	 * 统计今日新增用户数
	 * 
	 * @return
	 */
	public Integer countByToday() {
		return userMapper.countByToday(null);
	}

	/**
	 * 根据用户名和密码查询
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	@Cacheable(key = "#name", value = "user")
	public User findByNamePwd(String name, String pwd) {
		User user = userMapper.findByNamePwd(name, pwd);
		if (null != user) {
			// 用户信息写入缓存
			userCache.put(name, user);
			return user;
		}
		return null;
	}

	/**
	 * 批量插入用户组信息
	 * 
	 * @param list
	 * @return
	 */
	public void batchInsert(List<UserGroupMap> list) {
		userGroupMapMapper.batchInsert(list);
	}

}
