package com.bj.zl.learn.mybatis.mapper;


import com.bj.zl.learn.mybatis.model.IdCard;

public interface IdCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IdCard record);

    int insertSelective(IdCard record);

    IdCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IdCard record);

    int updateByPrimaryKey(IdCard record);
}