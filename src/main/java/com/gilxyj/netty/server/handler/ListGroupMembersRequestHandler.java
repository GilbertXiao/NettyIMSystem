package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.protocol.request.ListGroupMembersRequestPacket;
import com.gilxyj.netty.protocol.response.ListGroupMembersResponsePacket;
import com.gilxyj.netty.session.Session;
import com.gilxyj.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;

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
 * @create: 2020-10-20 22:12
 **/
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        //1.获取群的channelGroup
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        //2.遍历群成员的channel,对应的session,构造群成员的信息
        ArrayList<Session> sessions = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessions.add(session);
        }

        //3.构建获取成员列表响应写回客户端
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setSessionList(sessions);
        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
