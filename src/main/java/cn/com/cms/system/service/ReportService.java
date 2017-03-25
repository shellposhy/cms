package cn.com.cms.system.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.com.cms.data.model.DataTable;
import cn.com.cms.data.service.DataTableService;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.user.model.User;
import cn.com.cms.user.service.UserService;

/**
 * 统计服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ReportService {
	@Resource
	private DataTableService dataTableService;
	@Resource
	private LibraryDataService dataService;
	@Resource
	private UserService userService;

	/**
	 * 统计数据库用户添加的数据
	 * 
	 * @param baseId
	 * @return
	 */
	public Map<String, Integer> countUserData(Integer baseId) {
		Map<String, Integer> map = Maps.newHashMap();
		if (null != baseId) {
			List<DataTable> dataTables = dataTableService.findByBaseId(baseId);
			List<User> users = userService.findAlive(null);
			if (null != dataTables && dataTables.size() > 0) {
				for (int i = 0; i < dataTables.size(); i++) {
					Map<String, Integer> temp = dataService.count(dataTables.get(0).getName(), users);
					if (null != temp && temp.size() > 0) {
						if (i == 0) {
							for (String key : temp.keySet()) {
								map.put(key, temp.get(key));
							}
						} else {
							for (String key : temp.keySet()) {
								Integer value = temp.get(key) + map.get(key);
								map.put(key, value);
							}
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 获得所有具有数据的表
	 */
	public List<DataTable> findAllDatasTable() {
		return dataTableService.findTablesByGroupByBaseId();
	}
}
