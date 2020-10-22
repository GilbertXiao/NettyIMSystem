package com.gilxyj.netty.protocol.response;

import com.gilxyj.netty.protocol.Packet;
import com.gilxyj.netty.protocol.command.Command;
import com.gilxyj.netty.session.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * @create: 2020-10-22 20:19
 **/
@Data
@NoArgsConstructor
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private Session fromUser;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
    
}
