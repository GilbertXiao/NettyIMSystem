package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.protocol.request.GroupMessageRequestPacket;
import com.gilxyj.netty.protocol.response.GroupMessageResponsePacket;
import com.gilxyj.netty.session.Session;
import com.gilxyj.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

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
 * @create: 2020-10-22 20:22
 **/
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    private GroupMessageRequestHandler() {
    }
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String toGroupId = msg.getToGroupId();
        Channel fromChannel = ctx.channel();
        Session fromSession = SessionUtil.getSession(fromChannel);
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(toGroupId);
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(toGroupId);
        groupMessageResponsePacket.setFromUser(fromSession);
        groupMessageResponsePacket.setMessage(msg.getMessage());
        for (Channel channel : channelGroup) {
            if (channel != fromChannel) {
                channel.writeAndFlush(groupMessageResponsePacket);
            }
        }
    }
}
