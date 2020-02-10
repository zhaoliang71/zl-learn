package com.bj.zl.learn.tx.service.impl;

import com.bj.zl.learn.tx.mapper.GoodsMapper;
import com.bj.zl.learn.tx.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //更新库存
    //@Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKeyStore(Integer id)  {

        int update  = goodsMapper.updateByPrimaryKeyStore(id);
        //updateByPrimaryKeyStore1(id);
        return update;
    }
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public int updateByPrimaryKeyStore1(Integer id)  {

        int update  = goodsMapper.updateByPrimaryKeyStore(id);



        //运行时异常
        int a = 1 / 0;

        return update;
    }
}
