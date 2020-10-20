package com.gilxyj.netty.client.console;

import com.gilxyj.netty.protocol.request.ListGroupMembersRequestPacket;
import com.gilxyj.netty.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.Channel;

import java.util.Scanner;

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
 * @create: 2020-10-20 23:19
 **/
public class ListGroupConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入groupId,获取群成员列表:");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();
        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
