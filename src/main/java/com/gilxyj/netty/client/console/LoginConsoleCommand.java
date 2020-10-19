package com.gilxyj.netty.client.console;

import com.gilxyj.netty.protocol.request.LoginRequestPacket;
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
 * @create: 2020-10-19 12:25
 **/
public class LoginConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.println("输入用户名登陆:");
        String username = scanner.nextLine();
        loginRequestPacket.setUsername(username);

        //密码使用默认的
        loginRequestPacket.setPassword("pwd");

        //发送登陆数据包
        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
