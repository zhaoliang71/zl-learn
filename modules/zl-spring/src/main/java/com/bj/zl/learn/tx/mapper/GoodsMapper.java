package com.bj.zl.learn.tx.mapper;

import com.bj.zl.learn.tx.model.Goods;

public interface GoodsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    //更新库存
    int updateByPrimaryKeyStore(Integer id);
}