package com.gilxyj.netty.serialize;


import com.gilxyj.netty.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java对象转成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
