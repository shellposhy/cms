package cn.com.cms.framework.base.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.microduo.index.lucene3.MdSortField;
import com.microduo.index.lucene3.SearchResult;

import cn.com.cms.common.CmsData;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.Result;

/**
 * 搜索引擎索引服务类
 * 
 * @author shishb
 * @version 1.0
 */
public interface LibraryDataDao {

	/**
	 * 获取数据
	 * 
	 * @param tableId
	 *            表ID
	 * @param dataId
	 *            数据ID
	 * @return
	 */
	public CmsData find(Integer tableId, Integer dataId);

	/**
	 * 获取数据
	 * 
	 * @param tableName
	 *            表名
	 * @param dataId
	 *            数据ID
	 * @return
	 */
	public CmsData find(DataTable table, Integer dataId);

	/**
	 * 获取数据里的图片链接列表
	 * 
	 * @param tableId
	 * @param dataId
	 * @return 图片链接列表
	 */
	public List<String> findDataImgs(Integer tableId, Integer dataId);

	/**
	 * 保存数据
	 * 
	 * @param data
	 * @return
	 */
	public int save(CmsData data, HttpServletRequest request);

	/**
	 * 删除数据
	 * 
	 * @param tableId
	 *            表ID
	 * @param dataId
	 *            数据ID
	 */
	public void delete(Integer tableId, Integer dataId);

	/**
	 * 删除数据
	 * 
	 * @param baseId
	 *            库ID
	 * @param uuid
	 *            数据UUID
	 */
	public void delete(int baseId, String uuid);

	/**
	 * 分页获取数据
	 * 
	 * @param tableName
	 *            表名
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> search(DataTable table, Integer firstResult, Integer maxResults);

	/**
	 * 分页获取数据
	 * 
	 * @param tableName
	 *            表名
	 * @param where
	 *            条件
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> search(DataTable table, String where, Integer firstResult, Integer maxResults);

	/**
	 * 分页获取最新数据
	 * 
	 * @param tableName
	 *            表名
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> searchLatestData(DataTable table, Integer firstResult, Integer maxResults);

	/**
	 * 分页获取带有图片的数据
	 * 
	 * @param tableName
	 *            表名
	 * @param where
	 *            条件
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> searchImgData(DataTable table, String where, Integer firstResult, Integer maxResults);

	/**
	 * 通过SQL查询数据
	 * 
	 * @param sql
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> select(String sql, Integer firstResult, Integer maxResults);

	/**
	 * 通过SQL查询数据
	 * 
	 * @param sql
	 * @param firstResult
	 *            起始记录
	 * @param maxResults
	 *            每页条数
	 * @return
	 */
	public Result<CmsData> select(DataTable table, String sql, Integer firstResult, Integer maxResults);

	/**
	 * 导入数据
	 * 
	 * @param dataList
	 * @return
	 */
	public List<Integer> importData(List<CmsData> dataList);

	/**
	 * 复制数据
	 * 
	 * @param targetLibraryId
	 *            目标的库ID
	 * @param srcDataMap
	 *            数据来源Map：Key为TableId，List为DataIds
	 */
	public void copy(int targetLibraryId, Map<Integer, List<Integer>> srcDataMap, HttpServletRequest request);

	/**
	 * 检索
	 * 
	 * @param queryString
	 *            Lucene查询语句
	 * @param numHits
	 *            数量级
	 * @param sortFields
	 *            排序字段
	 * @param hightLightFields
	 *            高亮字段
	 * @param firstResult
	 *            第一条
	 * @param maxResults
	 *            页大小
	 * @param baseId
	 *            库id
	 * @return 查询结果
	 */
	public SearchResult searchIndex(String queryString, Integer numHits, MdSortField[] sortFields,
			String[] hightLightFields, Integer firstResult, Integer maxResults, Integer... baseId);

	/**
	 * 
	 * 检索
	 * 
	 * @param queryString
	 *            Lucene查询语句
	 * @param firstResult
	 *            第一条
	 * @param maxResults
	 *            页大小
	 * @param baseId
	 *            库id
	 * @return 查询结果
	 */
	public SearchResult searchIndex(Integer firstResult, Integer maxResults, String queryString, Integer... baseId);

	/**
	 * 保存
	 * 
	 * @param baseId
	 *            库ID
	 * @param data
	 *            数据
	 * @param fieldList
	 *            字段列表
	 */
	public void saveIndex(int baseId, CmsData data, List<DataField> fieldList);

	/**
	 * 删除
	 * 
	 * @param tableId
	 *            表ID
	 * @param dataId
	 *            数据ID
	 */
	public void deleteIndex(Integer tableId, Integer dataId);

	/**
	 * 删除
	 * 
	 * @param baseId
	 *            库ID
	 * @param uuid
	 *            数据UUID
	 */
	public void deleteIndex(int baseId, String uuid);

	/**
	 * 获取索引路径
	 * 
	 * @param baseId
	 *            库ID
	 * @return
	 */
	public String getIndexPath(Integer baseId);
}
