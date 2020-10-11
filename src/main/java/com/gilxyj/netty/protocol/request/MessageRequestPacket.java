package com.gilxyj.netty.protocol.request;

import com.gilxyj.netty.protocol.Packet;
import com.gilxyj.netty.protocol.command.Command;
import lombok.Data;

import static com.gilxyj.netty.protocol.command.Command.*;

/**
 * @program: nettyIMSystem
 * @description:
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-10-11 19:00
 **/
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
