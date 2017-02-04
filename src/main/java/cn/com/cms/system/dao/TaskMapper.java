
package cn.com.cms.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.cms.system.contant.ETaskStatus;
import cn.com.cms.system.contant.ETaskType;
import cn.com.cms.system.model.Task;

/**
 * 任务服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface TaskMapper {
	List<Task> findAll();

	List<Task> findByName(String name);

	List<Task> findByCode(String code);

	Task find(Integer id);

	List<Task> findByAimAndBeforeStatus(@Param("aim") String aim, @Param("status") ETaskStatus status);

	void insert(Task task);

	void update(Task task);

	void updateStatus(Integer id, ETaskStatus status);

	void updateSubProgress(Integer id, String subName, int subProgress);

	void delete(Integer id);

	List<Task> search(@Param("name") String name, @Param("first") int first, @Param("size") int size);

	int count(@Param("name") String name);

	List<Task> findByTypeAndNotStatus(@Param("type") ETaskType type, @Param("status") ETaskStatus status);
}