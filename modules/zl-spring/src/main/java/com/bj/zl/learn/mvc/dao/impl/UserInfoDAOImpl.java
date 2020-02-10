package com.bj.zl.learn.mvc.dao.impl;

import com.bj.zl.learn.mvc.dao.UserInfoDAO;
import com.bj.zl.learn.mvc.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository("userInfoDAOImpl")
public class UserInfoDAOImpl implements UserInfoDAO {

	@Override
	public UserInfo getuser() {
		//省略查询数据库，直接构造一个userInfo对象
		UserInfo userInfo = new UserInfo();
		userInfo.setId(1099);
		userInfo.setName("张三丰");
		return userInfo;
	}
}