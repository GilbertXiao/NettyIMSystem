package com.gilxyj.netty.server;

import com.gilxyj.netty.protocol.Packet;
import com.gilxyj.netty.protocol.PacketCodeC;
import com.gilxyj.netty.protocol.request.LoginRequestPacket;
import com.gilxyj.netty.protocol.request.MessageRequestPacket;
import com.gilxyj.netty.protocol.response.LoginResponsePacket;
import com.gilxyj.netty.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @program: nettylearn
 * @description:
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-10-03 11:54
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ":收到客户端的登陆请求......");
            //登陆流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ":登陆成功");
            }else{
                loginResponsePacket.setReason("账户密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date()+":登陆失败！");
            }
            //登陆响应
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            //客户端发来的消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ":收到客户端发来的消息:" + messageRequestPacket.getMessage());

            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket packet){
        return true;
    }
}
