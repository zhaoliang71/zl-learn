package com.bj.zl.learn.mybatis.mapper;


import com.bj.zl.learn.mybatis.model.OOrderInfo;

public interface OOrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OOrderInfo record);

    int insertSelective(OOrderInfo record);

    OOrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OOrderInfo record);

    int updateByPrimaryKey(OOrderInfo record);
}