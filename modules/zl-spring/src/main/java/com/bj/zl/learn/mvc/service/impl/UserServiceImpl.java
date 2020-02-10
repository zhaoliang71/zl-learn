package com.bj.zl.learn.mvc.service.impl;


import com.bj.zl.learn.mvc.dao.UserInfoDAO;
import com.bj.zl.learn.mvc.model.UserInfo;
import com.bj.zl.learn.mvc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDAO userInfoDAOImpl;

	public UserInfo getuser() {
		return userInfoDAOImpl.getuser();
	}
}