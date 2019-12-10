package com.bj.zl.learn.mybatis.mapper;


import com.bj.zl.learn.mybatis.model.UUserInfo;

public interface UUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UUserInfo record);

    int insertSelective(UUserInfo record);

    UUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UUserInfo record);

    int updateByPrimaryKey(UUserInfo record);
}