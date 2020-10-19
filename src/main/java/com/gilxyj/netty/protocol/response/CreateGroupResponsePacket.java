package com.gilxyj.netty.protocol.response;

import com.gilxyj.netty.protocol.Packet;
import com.gilxyj.netty.protocol.command.Command;
import lombok.Data;

import java.util.List;

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
 * @create: 2020-10-19 11:35
 **/
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
