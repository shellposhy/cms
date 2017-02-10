package cn.com.cms.test.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.cms.user.dao.UserMapper;

/**
 * 用户连接数据库测试用例
 * 
 * @author shishb
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class UserDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource
	private UserMapper userMapper;

	@Test
	public void selectTest() {
		System.out.println(userMapper.findAll().size());
	}
}
