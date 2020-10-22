package com.gilxyj.netty.client.handler;

import com.gilxyj.netty.protocol.request.HeartbeatRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

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
 * @create: 2020-10-23 00:19
 **/
@ChannelHandler.Sharable
public class HeartBeatTimeHandler extends ChannelInboundHandlerAdapter {

    public static final int HEARTBEART_INTERVAL = 5;

    public static final HeartBeatTimeHandler INSTANCE = new HeartBeatTimeHandler();

    private HeartBeatTimeHandler() {
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartbeatRequestPacket());
                //rerun
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEART_INTERVAL, TimeUnit.SECONDS);

    }
}
