package com.bj.zl.learn.mybatis.mapper;


import com.bj.zl.learn.mybatis.model.Person;

public interface PersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    Person selectById(Integer id);
}