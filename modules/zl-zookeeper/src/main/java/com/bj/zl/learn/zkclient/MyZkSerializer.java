/**
 * create by Administrator
 * 2019-08-27
 */
package com.bj.zl.learn.zkclient;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * 制定自己的序列化器
 */
public class MyZkSerializer implements ZkSerializer {
    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        return o.toString().getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return String.valueOf(bytes);
    }
}
