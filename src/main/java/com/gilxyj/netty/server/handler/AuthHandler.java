package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
 * @create: 2020-10-17 18:01
 **/
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("无登陆验证，强制关闭连接!");
        }else {
            System.out.println("当前连接登陆验证完毕，无需再次验证，AuthHandler被移除");
        }
    }
}
