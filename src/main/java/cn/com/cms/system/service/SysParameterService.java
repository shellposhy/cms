package cn.com.cms.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.cms.framework.base.Result;
import cn.com.cms.system.contant.ESysParamType;
import cn.com.cms.system.dao.SysParameterMapper;
import cn.com.cms.system.model.SysParameter;

/**
 * 系统初始化参数服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class SysParameterService {

	@Resource
	private SysParameterMapper sysParameterMapper;

	public void insert(SysParameter sysParameter) {
		sysParameterMapper.insert(sysParameter);
	}

	public SysParameter find(int id) {
		return sysParameterMapper.find(id);
	}

	public List<SysParameter> findAll() {
		return sysParameterMapper.findAll();
	}

	public List<SysParameter> findByParamType(ESysParamType paramType) {
		return sysParameterMapper.findByParamType(paramType);
	}

	public void update(SysParameter sysParameter) {
		sysParameterMapper.update(sysParameter);
	}

	public Result<SysParameter> result(String word, Integer first, Integer size) {
		return new Result<SysParameter>(sysParameterMapper.findByWord(word, first, size),
				sysParameterMapper.count(word));
	}

	public void delete(int id) {
		sysParameterMapper.delete(id);
	}
}
