package cn.com.cms.view.dao;

import cn.com.cms.framework.base.dao.BaseDao;
import cn.com.cms.view.model.ViewFormat;

/**
 * 内容格式Mapper类
 * 
 * @author shishb
 * @version 1.0
 */
public interface ViewFormatMapper extends BaseDao<ViewFormat> {
	ViewFormat findByDBId(Integer dbId);
}
