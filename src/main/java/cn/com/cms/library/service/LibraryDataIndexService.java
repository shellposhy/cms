package cn.com.cms.library.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import cn.com.cms.data.dao.DataTableMapper;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.constant.EIndexType;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.pepper.Configuration;
import cn.com.pepper.PepperException;
import cn.com.pepper.analyzer.IAnalyzer;
import cn.com.pepper.analyzer.PaodingAnalyzer;
import cn.com.pepper.common.PepperResult;
import cn.com.pepper.comparator.base.PepperSortField;
import cn.com.pepper.service.IndexDao;
import cn.com.pepper.service.IndexService;
import cn.com.cms.data.util.DataUtil;
import cn.com.cms.framework.base.CmsData;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.framework.config.SystemConstant;

@Service
public class LibraryDataIndexService {
	private static final Logger logger = Logger.getLogger(LibraryDataIndexService.class);

	@Resource
	protected AppConfig appConfig;
	@Resource
	protected DataTableMapper dataTableMapper;

	protected IndexDao indexService;

	@PostConstruct
	public void init() {
		String dicHome = appConfig.getAppPathHome() + "/dic";
		Configuration indexConfig = new Configuration();
		indexConfig.setAnalyzerFactory(new PaodingAnalyzer(dicHome));
		indexConfig.setHightLightPreTag(SystemConstant.HIGHT_LIGHT_PRE_TAG);
		indexConfig.setHightLightPostTag(SystemConstant.HIGHT_LIGHT_POST_TAG);
		indexConfig.setHightLightAnalyzerMode(IAnalyzer.MAX_WORD_LENGTH_MODE);
		indexService = IndexService.getInstance(SystemConstant.PDS3_DB_INDEX_SERVICE);
		indexService.setIndexConfig(indexConfig);
	}

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
	 *            数据库id
	 * @return 查询结果
	 */
	public PepperResult searchIndex(String queryString, Integer numHits, PepperSortField[] sortFields,
			String[] hightLightFields, Integer firstResult, Integer maxResults, Integer... baseId) {
		PepperResult result = new PepperResult();
		if (null == queryString || queryString.isEmpty()) {
			queryString = "*:*";
		}
		logger.debug("查询索引，查询条件：" + queryString);
		try {
			if (1 == baseId.length) {
				result = indexService.search(this.getIndexPath(baseId[0]), queryString, numHits, sortFields,
						hightLightFields, firstResult, maxResults);
			} else {
				return indexService.mSearch(getIndexPaths(baseId), queryString, numHits, sortFields, hightLightFields,
						firstResult, maxResults);
			}
		} catch (PepperException e) {
			result.documents = null;
			result.totalHits = 0;
			return result;
		} catch (NullPointerException e) {
			result.documents = null;
			result.totalHits = 0;
			return result;
		}
		return result;
	}

	/**
	 * 搜索索引数据
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param queryString
	 * @param baseId
	 * @return
	 */
	public PepperResult searchIndex(Integer firstResult, Integer maxResults, String queryString, Integer... baseId) {
		PepperSortField[] sortFields = {
				new PepperSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		int indexNumHits = appConfig.getDefaultIndexSearchNumHits();
		return searchIndex(queryString, indexNumHits, sortFields, null, firstResult, maxResults, baseId);
	}

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
	public void saveIndex(int baseId, CmsData data, List<DataField> fieldList, HttpServletRequest request) {
		List<DataField> indexFieldList = new ArrayList<DataField>();
		indexFieldList.addAll(fieldList);
		try {
			if (null != data.get(FieldCodes.DOC_TIME)) {
				indexFieldList
						.add(new DataField(FieldCodes.DOC_YEAR, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				indexFieldList
						.add(new DataField(FieldCodes.DOC_MONTH, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				indexFieldList
						.add(new DataField(FieldCodes.DOC_DAY, EDataType.Int, EIndexType.NotAnalyzedNoNorms, true));
				Date docTime = (Date) data.get(FieldCodes.DOC_TIME);
				data.put(FieldCodes.DOC_YEAR, DateTimeUtil.getYear(docTime));
				data.put(FieldCodes.DOC_MONTH, DateTimeUtil.getMonth(docTime) + 1);
				data.put(FieldCodes.DOC_DAY, DateTimeUtil.getDay(docTime));
			}
			if (null == data.getId()) {
				indexService.addDocument(getIndexPath(baseId), DataUtil.getIndexDoc(data, indexFieldList));
			} else {
				indexService.updateDocument(getIndexPath(baseId), FieldCodes.UUID, (String) data.get(FieldCodes.UUID),
						DataUtil.getIndexDoc(data, indexFieldList));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 删除
	 * 
	 * @param tableId
	 *            表ID
	 * @param dataId
	 *            数据ID
	 */
	public void deleteIndex(Integer tableId, Integer dataId) {
		DataTable dataTable = this.dataTableMapper.find(tableId);
		StringBuilder sb = new StringBuilder();
		sb.append("#int#").append(FieldCodes.TABLE_ID).append(":(").append(tableId).append(") AND #int#")
				.append(FieldCodes.ID).append(":(").append(dataId).append(")");
		try {
			this.indexService.deleteDocuments(getIndexPath(dataTable.getBaseId()), sb.toString());
		} catch (PepperException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 删除
	 * 
	 * @param baseId
	 *            数据库ID
	 * @param uuid
	 *            数据UUID
	 */
	public void deleteIndex(int baseId, String uuid) {
		String indexPath = getIndexPath(baseId);
		try {
			PepperResult result = indexService.search(indexPath, "UUID:" + uuid, 0, 1);
			if (result != null && result.documents != null) {
				for (Document doc : result.documents) {
					Integer tableId = Integer.valueOf(doc.get(FieldCodes.TABLE_ID));
					Integer dataId = Integer.valueOf(doc.get(FieldCodes.ID));
					deleteIndex(tableId, dataId);
				}
			}
		} catch (PepperException e) {
			logger.error("删除数据出错", e);
		}
	}

	/**
	 * 获取索引路径
	 * 
	 * @param baseId
	 * @return
	 */
	public String getIndexPath(Integer baseId) {
		return this.appConfig.getAppPathHome() + "/idx/" + baseId;
	}

	/**
	 * 获取多个库的索引路径
	 * 
	 * @param baseIds
	 * @return
	 */
	protected String[] getIndexPaths(Integer[] baseIds) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < baseIds.length; i++) {
			list.add(getIndexPath(baseIds[i]));
		}
		return list.toArray(new String[list.size()]);
	}
}