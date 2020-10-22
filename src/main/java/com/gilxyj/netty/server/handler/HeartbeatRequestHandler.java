package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.protocol.request.HeartbeatRequestPacket;
import com.gilxyj.netty.protocol.response.HeartbeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
 * @create: 2020-10-23 00:25
 **/
@ChannelHandler.Sharable
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {

    private HeartbeatRequestHandler() {
    }

    public static final HeartbeatRequestHandler INSTANCE = new HeartbeatRequestHandler();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequestPacket msg) throws Exception {
/*        int i = ctx.channel().hashCode();
        System.out.println(i);*/
        ctx.writeAndFlush(new HeartbeatResponsePacket());
    }
}
