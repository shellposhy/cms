package cn.com.cms.system.vo;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.com.cms.util.CollectionUtil;

/**
 * 饼图实体VO
 * 
 * @author shishb
 * @version 1.0
 */
public class PieChartVo {
	private String label;
	private int data;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	/**
	 * 把数据转换为饼图数据
	 * 
	 * @param timePeroid
	 * @param datas
	 * @param size
	 * @return
	 */
	public static ReportVo<PieChartVo> changeValue2PieChart(int timePeroid, Map<String, Integer> datas, int size) {
		ReportVo<PieChartVo> result = new ReportVo<PieChartVo>();
		if (null != datas && datas.size() > 0) {
			List<PieChartVo> dataList = Lists.newArrayList();
			List<PieChartVo> data = Lists.newArrayList();
			datas = CollectionUtil.sort(datas, false);
			int totalVisits = 0;
			for (String key : datas.keySet()) {
				totalVisits = totalVisits + datas.get(key).intValue();
				PieChartVo pie = new PieChartVo();
				pie.setData(datas.get(key));
				pie.setLabel(key);
				dataList.add(pie);
			}
			if (dataList.size() <= size) {
				data.addAll(dataList);
			} else {
				for (int i = 0; i < size; i++) {
					data.add(dataList.get(i));
				}
			}
			result.setData(data);
			if (timePeroid == 1) {
				result.setInfo("");
			} else {
				result.setInfo(" 录入数据最多人是【" + dataList.get(0).getLabel() + "】分布");
			}
		}
		return result;
	}
}
