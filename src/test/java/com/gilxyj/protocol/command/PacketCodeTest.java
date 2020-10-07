package com.gilxyj.protocol.command;


import com.gilxyj.netty.command.LoginRequestPacket;
import com.gilxyj.netty.command.Packet;
import com.gilxyj.netty.command.PacketCodeC;
import com.gilxyj.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: nettylearn
 * @description:
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-10-06 20:48
 **/
public class PacketCodeTest {

    @Test
    public void encode(){
        JSONSerializer jsonSerializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodePacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(jsonSerializer.serialize(loginRequestPacket), jsonSerializer.serialize(decodePacket));


    }
}
