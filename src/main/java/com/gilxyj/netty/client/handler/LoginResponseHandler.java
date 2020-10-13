package com.gilxyj.netty.client.handler;

import com.gilxyj.netty.protocol.request.LoginRequestPacket;
import com.gilxyj.netty.protocol.response.LoginResponsePacket;
import com.gilxyj.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

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
 * @create: 2020-10-13 21:54
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端开始登陆");

        //创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("GilbertXiao");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date()+":客户端登陆成功");
        }else {
            System.out.println(new Date()+":客户端登陆失败，原因："+loginResponsePacket.getReason());
        }
    }
}
