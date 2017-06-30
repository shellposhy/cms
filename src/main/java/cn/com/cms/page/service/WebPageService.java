package cn.com.cms.page.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.com.cms.data.util.DataUtil;
import cn.com.cms.data.util.DataVo;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.base.table.FieldCodes;
import cn.com.cms.framework.config.AppConfig;
import cn.com.cms.library.constant.EDataType;
import cn.com.cms.library.model.DataBase;
import cn.com.cms.library.service.LibraryDataService;
import cn.com.cms.library.service.LibraryService;
import cn.com.cms.page.util.PagingUtil;
import cn.com.pepper.common.PepperResult;
import cn.com.pepper.comparator.base.PepperSortField;

@Service
public class WebPageService {
	@Resource
	private AppConfig appConfig;
	@Resource
	private LibraryDataService libraryDataService;
	@Resource
	private LibraryService<DataBase> libraryService;

	/**
	 * Search the peer library list
	 * 
	 * @param libraryId
	 * @return
	 */
	public List<DataBase> listPeerLibrary(Integer libraryId) {
		List<DataBase> result = Lists.newArrayList();
		DataBase database = findLibrary(libraryId);
		Integer parentId = database.getParentID();
		if (parentId != 0) {
			result = libraryService.findByParentId(parentId);
		} else {
			result.add(database);
		}
		return result;
	}

	/**
	 * Paging utilities
	 * 
	 * @param curNum
	 * @param count
	 * @return
	 */
	public PagingUtil paging(int curNum, int count) {
		return new PagingUtil(appConfig.getAdminDataTablePageSize(), curNum, count);
	}

	/**
	 * Search the library on id
	 * 
	 * @param libraryId
	 * @return
	 */
	public DataBase findLibrary(Integer libraryId) {
		return libraryService.find(libraryId);
	}

	/**
	 * Search paging data in lucene
	 * 
	 * @param libraryId
	 * @param pageNum
	 * @param word
	 * @return
	 */
	public Result<DataVo> listData(int libraryId, int pageNum, String word) {
		assert pageNum > 0;
		Result<DataVo> result = new Result<DataVo>(null, 0);
		pageNum = pageNum < 1 ? 1 : pageNum;
		int size = appConfig.getAdminDataTablePageSize();
		int start = (pageNum - 1) * size;
		PepperSortField[] sortFields = {
				new PepperSortField(FieldCodes.DOC_TIME, DataUtil.dataType2SortType(EDataType.DateTime), true) };
		PepperResult searchResult = null;
		if (!Strings.isNullOrEmpty(word)) {
			String queryString = "Title:" + word.trim() + " OR Content:" + word.trim();
			String[] hightLightFields = { FieldCodes.TITLE };
			searchResult = libraryDataService.searchIndex(queryString, appConfig.getDefaultIndexSearchNumHits(),
					sortFields, hightLightFields, start, size, libraryId);
		} else {
			String queryString = "*:*";
			searchResult = libraryDataService.searchIndex(queryString, appConfig.getDefaultIndexSearchNumHits(),
					sortFields, null, start, size, libraryId);
		}
		if (null != searchResult && null != searchResult.documents) {
			List<DataVo> dataList = Lists.newArrayList();
			for (Document doc : searchResult.documents) {
				DataVo dataVo = new DataVo(doc);
				Integer tableId = Integer.valueOf(doc.get(FieldCodes.TABLE_ID));
				Integer dataId = Integer.valueOf(doc.get(FieldCodes.ID));
				if (!Strings.isNullOrEmpty(doc.get(FieldCodes.IMGS))) {
					if (Integer.valueOf(doc.get(FieldCodes.IMGS)).intValue() > 0) {
						List<String> imgs = libraryDataService.findDataImgs(tableId, dataId);
						if (null != imgs && imgs.size() > 0) {
							dataVo.setImg(imgs.get(0));
						}
					}
				}
				dataList.add(dataVo);
				result.setList(dataList);
				result.setTotalCount(searchResult.totalHits);
			}
		}
		return result;
	}
}
