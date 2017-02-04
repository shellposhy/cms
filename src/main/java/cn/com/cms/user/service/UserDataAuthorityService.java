package cn.com.cms.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.library.constant.ELibraryType;
import cn.com.cms.library.model.BaseLibrary;
import cn.com.cms.user.constant.EActionType;
import cn.com.cms.user.dao.UserDataAuthorityMapper;
import cn.com.cms.user.dao.UserGroupMapper;
import cn.com.cms.user.model.UserDataAuthority;
import cn.com.cms.user.model.UserGroup;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 用户数据权限服务类
 * 
 * @author shish
 * @version 1.0
 */
@Service
public class UserDataAuthorityService {

	@Resource
	private UserDataAuthorityMapper userDataAuthorityMapper;
	@Resource
	private UserGroupMapper userGroupMapper;

	public List<UserDataAuthority> findByUserId(Integer userId, ELibraryType type) {
		List<UserDataAuthority> result = new ArrayList<UserDataAuthority>();
		List<UserGroup> groups = userGroupMapper.findByUserId(userId);
		for (UserGroup userGroup : groups) {
			result.addAll(userDataAuthorityMapper.findByGroupId(userGroup.getId(), type));
		}
		return result;
	}

	public List<Integer> findDbIdsByUserId(Integer userId, ELibraryType type) {
		List<Integer> result = new ArrayList<Integer>();
		List<UserDataAuthority> list = findByUserId(userId, type);
		for (UserDataAuthority userDataAuthority : list) {
			result.add(userDataAuthority.getObjId());
		}
		return result;
	}

	/**
	 * 根据全县类型获取相应权限的数据库Id
	 * 
	 * @param userId
	 * @param actionType
	 * @return
	 * 
	 * @author gj
	 */
	public List<Integer> findAppDbIdsByUser(Integer userId, EActionType actionType) {
		List<UserDataAuthority> result = new ArrayList<UserDataAuthority>();
		List<UserGroup> groups = userGroupMapper.findByUserId(userId);
		boolean flagVote = false;
		for (UserGroup userGroup : groups) {
			if (userGroup.isAllDataAuthority()) {
				flagVote = true;
				break;
			}
		}
		List<Integer> ids = new ArrayList<Integer>();
		if (flagVote) {
			// ids = libraryService.findAllIds(ELibraryType.values());
		} else {
			for (UserGroup userGroup : groups) {
				result.addAll(userDataAuthorityMapper.findByGroupId(userGroup.getId(), null));
			}

			for (UserDataAuthority userDataAuthority : result) {
				if (null != userDataAuthority.getAllowActionType()
						&& userDataAuthority.getAllowActionType().contains(actionType.toString()))
					ids.add(userDataAuthority.getObjId());
			}
		}
		return ids;
	}

	public Integer[] getLibraryIds(List<UserDataAuthority> list) {
		List<Integer> idList = new ArrayList<Integer>();

		for (UserDataAuthority userDataAuthority : list) {
			idList.add(userDataAuthority.getObjId());
		}

		Integer[] result = new Integer[idList.size()];

		return idList.toArray(result);
	}

	public Integer[] getLibraryIds(List<UserDataAuthority> list, EActionType type) {
		List<Integer> idList = new ArrayList<Integer>();
		for (UserDataAuthority userDataAuthority : list) {
			if (null != userDataAuthority.getAllowActionType()
					&& userDataAuthority.getAllowActionType().contains(type.toString()))
				idList.add(userDataAuthority.getObjId());
		}
		Integer[] result = new Integer[idList.size()];
		return idList.toArray(result);
	}

	public List<UserDataAuthority> findByGroupId(Integer groupId, ELibraryType type) {
		return userDataAuthorityMapper.findByGroupId(groupId, type);
	}

	public List<UserDataAuthority> findAll() {
		return userDataAuthorityMapper.findAll();
	}

	public void deleteByGroupID(Integer groupId) {
		userDataAuthorityMapper.deleteByGroupID(groupId);
	}

	public void batchInsert(List<UserGroup> groups, BaseLibrary<?> library) {
		List<UserDataAuthority> userDataAuthorities = new ArrayList<UserDataAuthority>();
		for (UserGroup group : groups) {
			UserDataAuthority userDataAuthority = new UserDataAuthority();
			userDataAuthority.setGroupId(group.getId());// 用户组Id
			userDataAuthority.setAllDataTime(true);// AllDataTime
			userDataAuthority.setObjId(library.getId());// 数据库权限ID
			userDataAuthority.setCreateTime(DateTimeUtil.getCurrentDateTime());// 创建时间
			userDataAuthority.setUpdateTime(DateTimeUtil.getCurrentDateTime());// 更新时间
			userDataAuthority.setCreatorId(library.getCreatorId());// 创建用户组用户编号
			userDataAuthority.setUpdaterId(library.getUpdaterId());
			userDataAuthority.setObjType(library.getType());
			userDataAuthorities.add(userDataAuthority);
		}
		userDataAuthorityMapper.batchInsert(userDataAuthorities);
	}

}
