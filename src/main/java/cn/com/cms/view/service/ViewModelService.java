package cn.com.cms.view.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.com.cms.base.AppConfig;
import cn.com.cms.framework.base.Result;
import cn.com.cms.framework.template.TemplateComposer;
import cn.com.cms.view.constant.EModelType;
import cn.com.cms.view.dao.ViewItemMapper;
import cn.com.cms.view.dao.ViewModelCategoryMapper;
import cn.com.cms.view.dao.ViewModelMapper;
import cn.com.cms.view.model.ViewItem;
import cn.com.cms.view.model.ViewModel;
import cn.com.cms.view.model.ViewModelCategory;
import cn.com.people.data.util.FileUtil;

/**
 * 页面模板管理服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class ViewModelService {
	@Resource
	private AppConfig appConfig;
	@Resource
	public ViewItemMapper viewItemMapper;
	@Resource
	public ViewModelMapper viewModelMapper;
	@Resource
	private ViewModelCategoryMapper viewModelCategoryMapper;
	@Resource
	private ViewPageService viewPageService;

	/**
	 * 保存模板
	 * 
	 * @param viewModel
	 * @return
	 */
	public void save(ViewModel viewModel) {
		if (viewModel == null)
			return;
		if (viewModel.getId() == null)
			insert(viewModel);
		else
			update(viewModel);
	}

	/**
	 * 新增
	 * 
	 * @param viewModel
	 * @return
	 */
	public void insert(ViewModel viewModel) {
		viewModelMapper.insert(viewModel);
	}

	/**
	 * 更新
	 * 
	 * @param viewModel
	 * @return
	 */
	public void update(ViewModel viewModel) {
		viewModelMapper.update(viewModel);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			ViewModel model = viewModelMapper.find(id);
			try {
				FileUtils.deleteDirectory(new File(appConfig.getTemplatePath() + "/" + model.getCode()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			viewModelMapper.delete(id);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public Result<ViewModel> findByPage(int firstResult, int maxResult) {
		Result<ViewModel> result = new Result<ViewModel>();
		result.setTotalCount(viewModelMapper.count());
		result.setList(viewModelMapper.findByPage(firstResult, maxResult));
		return result;
	}

	/**
	 * 上传模板文件
	 * 
	 * @param file
	 * @param folderName
	 * @return
	 */
	public void uploadModel(MultipartFile file, String folderName) {
		String uploadFolderPath = appConfig.getAppPathHome() + "/templateTemp/";
		String destFolderPath = appConfig.getTemplatePath() + "/" + folderName;
		File uploadFolder = new File(uploadFolderPath);
		File destFolder = new File(destFolderPath);
		uploadFolder.mkdirs();
		destFolder.mkdirs();
		File uploadFile = new File(uploadFolderPath + file.getOriginalFilename());
		try {
			File parent = uploadFile.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			uploadFile.createNewFile();
			file.transferTo(uploadFile);
			if (uploadFile.getName().endsWith(".zip")) {
				FileUtil.unzip(uploadFolderPath + file.getOriginalFilename(), destFolderPath);
				uploadFile.delete();
				File[] files = destFolder.listFiles();
				for (File f : files) {
					String name = f.getName();
					String newName = new String(name.getBytes(), "UTF-8");
					String path = f.getAbsolutePath();
					path = path.substring(0, path.lastIndexOf(File.separator));
					path = path + File.separator + newName;
					f.renameTo(new File(path));
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新模板内容
	 * 
	 * @param id
	 * @return
	 */
	public void updateModelContent(Integer id) {
		ViewModel viewModel = find(id);
		String templateFilePath = appConfig.getTemplatePath() + "/" + viewModel.getCode();
		String content = TemplateComposer.readTemplate(templateFilePath + "/index.html");
		String classesPath = ViewPublishService.class.getResource("/").getPath();
		String targetPath = classesPath.substring(0, classesPath.indexOf("WEB-INF")) + "WEB-INF/"
				+ appConfig.getTemplateHome();
		String targetStaticPath = classesPath.substring(0, classesPath.indexOf("/WEB-INF"));
		String newPath = appConfig.getAppPath();
		switch (viewModel.getModelType()) {
		case Subject:
			targetPath += "/subject/" + viewModel.getCode();
			newPath += "/static/subject/" + viewModel.getCode();
			targetStaticPath += "/static/subject/" + viewModel.getCode();
			break;
		default:
			targetPath += "/" + viewModel.getCode();
			newPath += "/static/" + viewModel.getCode();
			targetStaticPath += "/static/" + viewModel.getCode();
			break;
		}
		FileUtil.createFile(targetPath, "index.html", TemplateComposer.replacePath(content, newPath), true);
		FileUtil.copyFolder(templateFilePath, targetStaticPath, true);
		List<ViewItem> list = TemplateComposer.findEditableArea(viewModel, content);
		for (ViewItem viewItem : list) {
			ViewItem item = viewItemMapper.findByModelIdAndCode(id, viewItem.getCode());
			if (null != item) {
				viewItem.setId(item.getId());
				viewItemMapper.update(viewItem);
			} else {
				viewItemMapper.insert(viewItem);
			}
		}
	}

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public ViewModel find(Integer id) {
		return viewModelMapper.find(id);
	}

	/**
	 * 获得所有模板目录
	 * 
	 * @return
	 */
	public List<ViewModelCategory> findAllCategory() {
		return viewModelCategoryMapper.findAll();
	}

	/**
	 * 根据分类编号查询
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<ViewModel> findModelByCategoryId(int categoryId) {
		return viewModelMapper.findByCategoryId(categoryId);
	}

	/**
	 * 主键查询分类
	 * 
	 * @param id
	 * @return
	 */
	public ViewModelCategory findCategoryById(int id) {
		return viewModelCategoryMapper.find(id);
	}

	/**
	 * 根据父节点查询
	 * 
	 * @param id
	 * @return
	 */
	public List<ViewModelCategory> findCategoryByParentId(int id) {
		return viewModelCategoryMapper.findByParentId(id);
	}

	/**
	 * 删除目录
	 * 
	 * @param id
	 * @return
	 */
	public void deleteCategory(int id) {
		viewModelCategoryMapper.delete(id);
	}

	/**
	 * 保存分类
	 * 
	 * @param category
	 * @return
	 */
	public void saveCategory(ViewModelCategory category) {
		if (category.getId() == null) {
			viewModelCategoryMapper.insert(category);
		} else {
			viewModelCategoryMapper.update(category);
		}
	}

	/**
	 * 根据类型查询
	 * 
	 * @param eModelType
	 * @return
	 */
	public List<ViewModel> findByType(EModelType modelType) {
		return viewModelMapper.findByType(modelType);
	}
}
