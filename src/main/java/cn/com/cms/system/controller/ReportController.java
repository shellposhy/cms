package cn.com.cms.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.collect.Lists;

import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.BaseController;
import cn.com.cms.framework.base.view.BaseMappingJsonView;
import cn.com.cms.framework.config.JsonPara;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.system.model.RecordVisit;
import cn.com.cms.system.service.RecordVisitService;
import cn.com.cms.system.service.ReportService;
import cn.com.cms.system.vo.BarLineChartVo;
import cn.com.cms.system.vo.PieChartVo;
import cn.com.cms.system.vo.ReportVo;
import cn.com.cms.user.service.UserGroupService;
import cn.com.cms.view.model.ViewPage;
import cn.com.cms.view.service.ViewPageService;

/**
 * 统计报告查询
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/report")
public class ReportController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ReportController.class.getName());
	@Resource
	public UserGroupService userGroupService;
	@Resource
	private RecordVisitService recordVisitService;
	@Resource
	private ReportService reportService;
	@Resource
	private ViewPageService pageService;

	public final static int REPORT_TYPE_BAR = 0; // 直方图
	public final static int REPORT_TYPE_LINE = 1;// 线形态
	public final static int REPORT_TYPE_PIE = 2;// 饼图

	@RequestMapping(method = RequestMethod.GET)
	public String report() {
		LOG.debug("===log.report===");
		return "/admin/log/report/global";
	}

	@RequestMapping("/list")
	public MappingJacksonJsonView list(@RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> jsonMap = JsonPara.getParaMap(jsonParas);
		Integer timePeroid = Integer.parseInt(jsonMap.get(JsonPara.DataTablesParaNames.timePeroid));
		List<ViewPage> projectList = pageService.findByStatus(EDataStatus.Yes.ordinal());
		List<ViewPage> result = Lists.newArrayList();
		if (null != projectList && projectList.size() > 0) {
			if (projectList.size() <= timePeroid.intValue()) {
				result.addAll(projectList);
			} else {
				for (int i = 0; i < timePeroid.intValue(); i++) {
					result.add(projectList.get(i));
				}
			}
		}
		mv.addStaticAttribute("result", result);
		return mv;
	}

	@RequestMapping("/chart")
	public MappingJacksonJsonView chart(@RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> jsonMap = JsonPara.getParaMap(jsonParas);
		Integer timePeroid = Integer.parseInt(jsonMap.get(JsonPara.DataTablesParaNames.timePeroid));
		String type = jsonMap.get(JsonPara.DataTablesParaNames.type);
		Date startDate = ReportVo.staticDateStartAndDateEnd(timePeroid).get(0);
		Date endDate = ReportVo.staticDateStartAndDateEnd(timePeroid).get(1);
		List<Object> dataList = Lists.newArrayList();
		if (Integer.valueOf(type).intValue() == REPORT_TYPE_BAR) {
			List<RecordVisit> recordVisits = recordVisitService.rankTimePeriodInUser(null, startDate, endDate, false,
					false, true, false);
			ReportVo<BarLineChartVo> reportVo = null;
			if (null != recordVisits && recordVisits.size() > 0) {
				reportVo = BarLineChartVo.changeValue2BarChart(timePeroid, recordVisits);
				StringBuilder titlebuilder = new StringBuilder();
				titlebuilder.append("系统").append(reportVo.getTitle()).append("浏览量柱状图");
				reportVo.setTitle(titlebuilder.toString());
			}
			dataList.add(reportVo);
		} else if (Integer.valueOf(type).intValue() == REPORT_TYPE_LINE) {
			List<RecordVisit> recordVisits = recordVisitService.rankTimePeriodInUser(null, startDate, endDate, false,
					false, false, true);
			ReportVo<BarLineChartVo> reportVo = null;
			if (null != recordVisits && recordVisits.size() > 0) {
				reportVo = BarLineChartVo.changeValue2LineChart(timePeroid, recordVisits);
				StringBuilder titlebuilder = new StringBuilder();
				titlebuilder.append("系统").append(reportVo.getTitle()).append("浏览量柱状图");
				reportVo.setTitle(titlebuilder.toString());
			}
			dataList.add(reportVo);
		} else if (Integer.valueOf(type).intValue() == REPORT_TYPE_PIE) {
			// 基础数据处理
			List<DataTable> tables = reportService.findAllDatasTable();
			Integer baseId = null != tables && tables.size() > 0 ? tables.get(0).getBaseId() : null;
			Map<String, Integer> map = reportService.countUserData(baseId);
			// 饼图数据处理
			// 只显示前9名的数据
			ReportVo<PieChartVo> reportVo = PieChartVo.changeValue2PieChart(timePeroid, map, 9);
			reportVo.setTitle("用户数据分部饼状图");
			dataList.add(reportVo);
		}
		mv.addStaticAttribute("result", dataList);
		return mv;
	}

}
