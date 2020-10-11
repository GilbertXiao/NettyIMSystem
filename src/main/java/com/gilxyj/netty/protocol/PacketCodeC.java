package com.gilxyj.netty.protocol;


import com.gilxyj.netty.protocol.command.Command;
import com.gilxyj.netty.protocol.request.LoginRequestPacket;
import com.gilxyj.netty.protocol.request.MessageRequestPacket;
import com.gilxyj.netty.protocol.response.LoginResponsePacket;
import com.gilxyj.netty.protocol.response.MessageResponsePacket;
import com.gilxyj.netty.serialize.Serializer;
import com.gilxyj.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

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
 * @create: 2020-10-05 18:05
 **/
public class PacketCodeC {

    private static final int MAGIX_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        JSONSerializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlogrithm(), jsonSerializer);
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);

    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        //1. 创建ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        //2. 序列化java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码过程
        byteBuf.writeInt(MAGIX_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;

    }

    public Packet decode(ByteBuf byteBuf) {
        //1. 跳过magic number
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }
}
