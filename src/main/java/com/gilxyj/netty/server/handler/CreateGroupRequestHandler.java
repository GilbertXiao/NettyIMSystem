package com.gilxyj.netty.server.handler;

import com.gilxyj.netty.protocol.request.CreateGroupRequestPacket;
import com.gilxyj.netty.protocol.response.CreateGroupResponsePacket;
import com.gilxyj.netty.session.Session;
import com.gilxyj.netty.util.IDUtil;
import com.gilxyj.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

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
 * @create: 2020-10-19 12:05
 **/
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    private CreateGroupRequestHandler() {
    }

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();

        ArrayList<String> userNameList = new ArrayList<>();

        //1.创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        //2.筛选出待加入群聊的用户的channel和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        //3.创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        String groupId = IDUtil.randomId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        //4.给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功,id为[" + createGroupResponsePacket.getGroupId() + "]");
        System.out.println("群里面有:" + createGroupResponsePacket.getUserNameList());

        //保留群组信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
