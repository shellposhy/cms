package cn.com.cms.system.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.system.dao.RecordDocMapper;
import cn.com.cms.system.model.RecordDoc;

/**
 * 访问文章服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class RecordDocService {
	@Resource
	private RecordDocMapper recordDocMapper;

	public void insert(RecordDoc record) {
		recordDocMapper.insert(record);
	}

	public RecordDoc find(int id) {
		return recordDocMapper.find(id);
	}

	/**
	 * 查看某一(或所有)用户最喜欢的文章排行
	 * 
	 * @param userId
	 *            用户id null:查询所有用户
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<RecordDoc> rankDocInUser(Integer userId, Date startTime, Date endTime, int firstResult, int maxResult) {
		return recordDocMapper.rankDocInUser(userId, startTime, endTime, firstResult, maxResult);
	}

	/**
	 * 查看某个组(或所有用户)最喜欢的文章排行
	 * 
	 * @param groupId
	 *            用户组id null:查询所有用户
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<RecordDoc> rankDocInUserGroup(Integer groupId, Date startTime, Date endTime, int firstResult,
			int maxResult) {
		return recordDocMapper.rankDocInUserGroup(groupId, startTime, endTime, firstResult, maxResult);
	}

	/**
	 * 查询某个数据库最受喜欢的文章排行
	 * 
	 * @param baseId
	 *            数据库id null:查所有数据库
	 * @param startTime
	 * @param endTime
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<RecordDoc> rankDocInDb(Integer baseId, Date startTime, Date endTime, int firstResult, int maxResult) {
		return recordDocMapper.rankDocInDb(baseId, startTime, endTime, firstResult, maxResult);
	}

	public void update(RecordDoc record) {
		recordDocMapper.update(record);
	}
}
