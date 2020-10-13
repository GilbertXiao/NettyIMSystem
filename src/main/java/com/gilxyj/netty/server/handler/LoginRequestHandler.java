package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.protocol.request.LoginRequestPacket;
import com.gilxyj.netty.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

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
 * @create: 2020-10-13 22:30
 **/
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ":收到客户端的登陆请求......");
        //登陆流程
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ":登陆成功");
        }else{
            loginResponsePacket.setReason("账户密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date()+":登陆失败！");
        }
        //登陆响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket packet){
        return true;
    }
}
