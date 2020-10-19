package com.gilxyj.netty.client.console;

import com.gilxyj.netty.client.console.ConsoleCommand;
import com.gilxyj.netty.protocol.request.MessageRequestPacket;
import com.gilxyj.netty.protocol.response.MessageResponsePacket;
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
 * @create: 2020-10-19 12:29
 **/
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息给某个用户:");
        System.out.println("请输入用户id和消息:");
        String toUserId = scanner.next();
        String msg = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, msg));
    }
}
